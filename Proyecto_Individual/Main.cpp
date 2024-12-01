#include <iostream>
#include <fstream>
#include <string>
#include <limits>
#include <vector>
#include <chrono>

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
        else if (choice == 3)
        {
            cout << "Volviendo al menú principal..." << endl;
            break;
        }
        else
        {
            cout << "Opción inválida, inténtelo nuevamente." << endl;
            // Clear the input buffer in case of invalid input
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

    // Back substitution
    for (int i = 0; i < n; i++)
    {
        x[i] = a[i][n] / a[i][i];
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
    cout << "3. Exit" << endl;
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