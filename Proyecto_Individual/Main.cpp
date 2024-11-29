#include <iostream>
#include <fstream>
#include <string>
#include <limits>
#include <vector>
using namespace std;

vector<vector<double>> a;
int n;
vector<double> x;

// Function prototypes
void generateRandomMatrix();
void readMatrixFromFile();
void showJacobiMenu();
void applyJacobiMethod();
vector<vector<double>> createAugmentedMatrix();
void eliminacionGaussiana(vector<vector<double>> &a, vector<double> &x);

int main()
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

    // Menú
    int choice, n;
    vector<vector<double>> a;
    vector<double> x;
    while (true)
    {
        // Menú principal
        cout << "===========================" << endl;
        cout << "1. Generate Random Matrix" << endl;
        cout << "2. Read Matrix From File" << endl;
        cout << "3. Matriz de ejemplo" << endl;
        cout << "4. Exit" << endl;
        cout << "Seleccione una opción: ";
        cin >> choice;

        if (choice == 1)
        {
            generateRandomMatrix();
            showJacobiMenu();
        }
        else if (choice == 2)
        {
            readMatrixFromFile();
            showJacobiMenu();
        }
        else if (choice == 3)
        {
            a = createAugmentedMatrix();
            n = a.size();
            x.resize(n, 0);
            showJacobiMenu();
        }
        else if (choice == 4)
        {
            cout << "Finalizando la ejecución del programa..." << endl;
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

    return 0;
}
vector<vector<double>> createAugmentedMatrix()
{
    // Define and return the augmented matrix for the system of equations
    // Example: 2x + y + z = 5, 4x + 5y + 6z = 8, 3x + 2y + 3z = 7
    return {
        {2, 1, 1, 5},
        {4, 5, 6, 8},
        {3, 2, 3, 7}};
}
// First-level menu options
void generateRandomMatrix()
{
    // Empty function for generating random matrix
    cout << "Generating random matrix..." << endl;
}

void readMatrixFromFile()
{
    // Empty function for reading matrix from file
    cout << "Reading matrix from file..." << endl;
}

// Second-level menu
void showJacobiMenu()
{
    int choice;

    while (true)
    {
        cout << "========================" << endl;
        cout << "1. Método de Jacobi" << endl;
        cout << "2. Eliminación Gaussiana" << endl;
        cout << "3. Exit" << endl;
        cout << "Please select an option: ";
        cin >> choice;

        if (choice == 1)
        {
            applyJacobiMethod();
            break; // After applying Jacobi method, return to the previous menu
        }
        else if (choice == 2)
        {
            eliminacionGaussiana(a, x);
            cout << "Solution: ";
            for (double xi : x)
            {
                cout << xi << " ";
            }
            cout << endl;
            break; // Exit the second menu
        }
        else if (choice == 3)
        {
            cout << "Volviendo al menú principal..." << endl;
            break; // Exit the second menu
        }
        else
        {
            cout << "Invalid choice, please try again." << endl;
            // Clear the input buffer in case of invalid input
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    }
}

void applyJacobiMethod()
{
    // Empty function for applying Jacobi method
    cout << "Aplicando el método de Jacobi..." << endl;
}

void eliminacionGaussiana(vector<vector<double>> &a, vector<double> &x)
{
    cout << "Aplicando Eliminación Gaussiana..." << endl;
    int n = a.size();

    // Forward elimination
    for (int k = 0; k < n; k++)
    {
        // Make the diagonal element a[k][k] to be the pivot
        for (int i = 0; i < n; i++)
        {
            if (k != i)
            {
                // Eliminate the element a[i][k] by subtracting a multiple of row k from row i
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