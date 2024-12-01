#include <iostream>
#include <fstream>
#include <string>
#include <limits>
#include <vector>
#include <chrono>
#include <omp.h>

#define N 3
#define MATRIX_SIZE
struct record_s
{
    double val;
    long prod;
    struct record_s *next_p;
};

struct buf_list
{
    struct record_s *head_p;
    struct record_s *tail_p;
};

struct buf_list buff[N];

long producers_done[N];

struct record_s *Create_record(long my_rank, double data)
{
    struct record_s *rec_p = (struct record_s *)malloc(sizeof(struct record_s));
    rec_p->val = data;
    rec_p->prod = my_rank;
    rec_p->next_p = NULL;
    return rec_p;
}

using namespace std;

vector<vector<double>> a;
int n;
vector<double> x;

void PrintMatrix(vector<vector<double>> &a);
void Banner();
void MenuPrincipalOpciones();
void MenuMetodosOpciones();
vector<vector<double>> generateRandomMatrix();
vector<vector<double>> readMatrixFromFile();
vector<vector<double>> createAugmentedMatrix();
void MenuMetodos();
void Jacobi();
void GaussJordan(vector<vector<double>> &a, vector<double> &x);
void GaussJordanOpenMP(vector<vector<double>> &a, vector<double> &x);
void Enqueue(long my_rank, struct record_s *rec_p)
{
    if (buff[my_rank].tail_p == NULL)
    {
        buff[my_rank].head_p = rec_p;
        buff[my_rank].tail_p = rec_p;
    }
    else
    {
        buff[my_rank].tail_p->next_p = rec_p;
        buff[my_rank].tail_p = rec_p;
    }
}
void Put(long my_rank, double data)
{
    struct record_s *rec_p;
    rec_p = Create_record(my_rank, data);

#pragma omp critical(queue)
    {
        Enqueue(my_rank, rec_p);
    }

#pragma omp critical(done)
    producers_done[my_rank]++;
}
struct record_s *Dequeue(long myrank)
{
    struct record_s *rec_p;
    if (buff[myrank].head_p == NULL)
    {
        return NULL;
    }
    else if (buff[myrank].head_p == buff[myrank].tail_p)
    {
        // One record in queue
        rec_p = buff[myrank].head_p;
        buff[myrank].head_p = buff[myrank].tail_p = NULL;
    }
    else
    {
        rec_p = buff[myrank].head_p;
        buff[myrank].head_p = buff[myrank].head_p->next_p;
    }
    return rec_p;
}
double Get(long my_rank)
{
    struct record_s *rec_p;
    double data;
    while (producers_done[my_rank] < 1 ||
           buff[my_rank].head_p != NULL)
    {
#pragma omp critical(queue)
        {
            rec_p = Dequeue(my_rank);
        }
        if (rec_p != NULL)
        {
            data = rec_p->val;
            free(rec_p);
            return data;
        }
    }
    return -1;
}
void Initialize_pipeline()
{
    for (int i = 0; i < N; i++)
    {
        buff[i].head_p = buff[i].tail_p = NULL;
        producers_done[i] = 0;
    }
}

int main()
{
    Banner();
    // Menú
    int choice;
    while (true)
    {
        // Menú principal
        MenuPrincipalOpciones();
        cin >> choice;

        if (choice == 1)
        {
            a = generateRandomMatrix();
            PrintMatrix(a);
            MenuMetodos();
        }
        else if (choice == 2)
        {
            a = readMatrixFromFile();
            PrintMatrix(a);
            MenuMetodos();
        }
        else if (choice == 3)
        {
            a = createAugmentedMatrix();
            PrintMatrix(a);
            MenuMetodos();
        }
        else if (choice == 4)
        {
            cout << "Finalizando la ejecución del programa..." << endl;
            break;
        }
        else
        {
            cout << "Opción inválida, inténtelo nuevamente." << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    }

    return 0;
}

// Menú de Métodos
void MenuMetodos()
{
    int choice;
    MenuMetodosOpciones();
    while (true)
    {

        cin >> choice;

        if (choice == 1)
        {
            Jacobi();
            // TODO: Imprimir la solución
            break;
        }
        // Gauss-Jordan: Serial
        else if (choice == 2)
        {
            auto start = std::chrono::high_resolution_clock::now();
            GaussJordan(a, x);
            auto end = std::chrono::high_resolution_clock::now();
            std::chrono::duration<double> duration = end - start;
            cout << "Solution: ";
            for (double xi : x)
            {
                cout << xi << " ";
            }
            cout << endl;
            cout << "Tiempo de ejecución: " << duration.count() * 1000 << " ms\n";
            break;
        }
        // Gauss-Jordan: Paralelo
        else if (choice == 3)
        {
            auto start = std::chrono::high_resolution_clock::now();
            GaussJordanOpenMP(a, x);
            auto end = std::chrono::high_resolution_clock::now();
            std::chrono::duration<double> duration = end - start;
            cout << "Solution: ";
            for (double xi : x)
            {
                cout << xi << " ";
            }
            cout << endl;
            cout << "Tiempo de ejecución: " << duration.count() * 1000 << " ms\n";
            break;
        }
        else if (choice == 4)
        {
            cout << "Volviendo al menú principal..." << endl;
            break;
        }
        else
        {
            cout << "Opción inválida, inténtelo nuevamente." << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    }
}

void Jacobi()
{
    // Empty function for applying Jacobi method
    cout << "Aplicando el método de Jacobi..." << endl;
}

void GaussJordan(vector<vector<double>> &a, vector<double> &x)
{
    n = a.size();
    x.resize(n, 0);
    cout << "Aplicando Eliminación Gaussiana..." << endl;
    for (int k = 0; k < n; k++)
    {
        for (int i = 0; i < n; i++)
        {
            if (k != i)
            {
                double factor = a[i][k] / a[k][k];
                for (int j = k + 1; j < n + 1; j++)
                {
                    a[i][j] -= factor * a[k][j];
                }
            }
        }
    }

    for (int i = 0; i < n; i++)
    {
        x[i] = a[i][n] / a[i][i];
    }
}
void GaussJordanOpenMP(vector<vector<double>> &a, vector<double> &x)
{
    n = a.size();
    x.resize(n, 0);
    cout << "Aplicando Eliminación Gaussiana con OpenMP..." << endl;

    Initialize_pipeline();

#pragma omp parallel
    {
        long my_rank = omp_get_thread_num();
        long bsize = n / omp_get_num_threads();
        double row;

        if (my_rank != 0)
        {
            for (long k = my_rank * bsize; k < (my_rank + 1) * bsize; k++)
            {
                row = Get(my_rank);
                Put(my_rank + 1, row);
                for (long i = 0; i < k + bsize; i++)
                {
                    if (row != i)
                    {
                        for (long j = k; j < n + 1; j++)
                        {
                            a[i][j] -= (a[i][row] / a[row][row]) * a[row][j];
                        }
                    }
                }
            }
        }
        else
        {
            for (long k = my_rank * bsize; k < (my_rank + 1) * bsize; k++)
            {
                Put(my_rank + 1, k);
                for (long i = 0; i < k + bsize; i++)
                {
                    if (k != i)
                    {
                        for (long j = k + 1; j < n + 1; j++)
                        {
                            a[i][j] -= (a[i][k] / a[k][k]) * a[k][j];
                        }
                    }
                }
            }
        }

#pragma omp barrier
#pragma omp single
        {
            for (long i = n - 1; i >= 0; i--)
            {
                x[i] = a[i][n];
                for (long j = i + 1; j < n; j++)
                {
                    x[i] -= a[i][j] * x[j];
                }
                x[i] /= a[i][i];
            }
        }
    }
}
vector<vector<double>> createAugmentedMatrix()
{
    return {
        {2, 1, 1, 5},
        {4, 5, 6, 8},
        {3, 2, 3, 7}};
}
vector<vector<double>> generateRandomMatrix()
{
    cout << "Generating random matrix..." << endl;
    return {};
}

vector<vector<double>> readMatrixFromFile()
{
    cout << "Reading matrix from file..." << endl;
    return {};
}
void PrintMatrix(vector<vector<double>> &a)
{
    cout << "Matrix:\n";
    for (int i = 0; i < a.size(); i++)
    {
        for (int j = 0; j < a[0].size(); j++)
        {
            cout << a[i][j] << " ";
        }
        cout << endl;
    }
}
void MenuMetodosOpciones()
{
    cout << "========================" << endl;
    cout << "1. Jacobi" << endl;
    cout << "2. Gauss-Jordan" << endl;
    cout << "3. Gauss-Jordan OpenMP" << endl;
    cout << "4. Exit" << endl;
    cout << "Seleccione una opción: ";
}
void MenuPrincipalOpciones()
{
    cout << "===========================" << endl;
    cout << "1. Generate Random Matrix" << endl;
    cout << "2. Read Matrix From File" << endl;
    cout << "3. Matriz de ejemplo" << endl;
    cout << "4. Exit" << endl;
    cout << "Seleccione una opción: ";
}
void Banner()
{
    cout << "+=================================================================+" << endl;
    cout << "|  ____                                  _               _        |" << endl;
    cout << "| |  _ \\  _ __  ___   _   _   ___   ___ | |_  ___     __| |  ___  |" << endl;
    cout << "| | |_) || '__|/ _ \\ | | | | / _ \\ / __|| __|/ _ \\   / _` | / _ \\ |" << endl;
    cout << "| |  __/ | |  | (_) || |_| ||  __/| (__ | |_| (_) | | (_| ||  __/ |" << endl;
    cout << "| |_|__  |_|   \\___/  \\__, |_\\___| \\___| \\__|\\___/   \\__,_| \\___| |" << endl;
    cout << "| |  _ \\  __ _  _ __  |___/| |  ___ | |  __ _                     |" << endl;
    cout << "| | |_) |/ _` || '__|/ _` || | / _ \\| | / _` |                    |" << endl;
    cout << "| |  __/| (_| || |  | (_| || ||  __/| || (_| |                    |" << endl;
    cout << "| |_|    \\__,_||_|   \\__,_||_| \\___||_| \\__,_|                    |" << endl;
    cout << "+=================================================================+" << endl;
}
