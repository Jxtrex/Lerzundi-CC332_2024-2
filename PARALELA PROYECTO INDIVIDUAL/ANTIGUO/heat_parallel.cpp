#include "mpi.h"
#include <iostream>
#include <vector>
#include <cmath>
#include <corecrt_math_defines.h>

using namespace std;

// Parámetros
const double kappa = 0.1;
const double L = 1.0;
const double T = 1.0;
const int n = 100;
const int m = 1000;

double f(double x, double t) {
    return 0.0; // Función de fuente de calor
}

double initialCondition(double x) {
    return sin(M_PI * x); // Condición inicial
}

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    double dx = L / (n - 1);
    double dt = T / m;
    double alpha = kappa * dt / (dx * dx);

    int local_n = n / size; // Número de puntos por procesador
    if (rank == size - 1) local_n += n % size; // Último procesador se lleva el resto

    vector<double> u_prev(local_n + 2, 0.0); // Incluye puntos fantasmas
    vector<double> u(local_n + 2, 0.0);

    // Inicialización de las condiciones iniciales
    for (int i = 1; i <= local_n; ++i) {
        double x = (rank * (n / size) + i - 1) * dx;
        u_prev[i] = initialCondition(x);
    }

    // Iteración en el tiempo
    for (int t = 0; t < m; ++t) {
        // Enviar/Recibir valores de frontera
        if (rank > 0) {
            MPI_Send(&u_prev[1], 1, MPI_DOUBLE, rank - 1, 0, MPI_COMM_WORLD);
            MPI_Recv(&u_prev[0], 1, MPI_DOUBLE, rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        if (rank < size - 1) {
            MPI_Send(&u_prev[local_n], 1, MPI_DOUBLE, rank + 1, 0, MPI_COMM_WORLD);
            MPI_Recv(&u_prev[local_n + 1], 1, MPI_DOUBLE, rank + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        // Cálculo en puntos internos
        for (int i = 1; i <= local_n; ++i) {
            u[i] = u_prev[i] + alpha * (u_prev[i - 1] - 2 * u_prev[i] + u_prev[i + 1])
                   + dt * f((rank * (n / size) + i - 1) * dx, t * dt);
        }

        // Actualización de valores
        u_prev.swap(u);
    }

    // Recolección de resultados
    vector<double> result;
    if (rank == 0) result.resize(n);

    vector<double> local_result(local_n);
    for (int i = 0; i < local_n; ++i) {
        local_result[i] = u_prev[i + 1];
    }

    MPI_Gather(local_result.data(), local_n, MPI_DOUBLE, result.data(), local_n, MPI_DOUBLE, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        for (int i = 0; i < n; ++i) {
            cout << "u(" << i * dx << ") = " << result[i] << endl;
        }
    }

    MPI_Finalize();
    return 0;
}
