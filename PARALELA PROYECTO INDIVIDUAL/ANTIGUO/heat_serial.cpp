#include <iostream>
#include <vector>
#include <cmath>
#include <corecrt_math_defines.h>

using namespace std;

// Parámetros
const double kappa = 0.1;   // Conductividad térmica
const double L = 1.0;       // Longitud de la barra
const double T = 1.0;       // Tiempo total
const int n = 100;          // Número de puntos espaciales
const int m = 1000;         // Número de pasos de tiempo

double f(double x, double t) {
    return 0.0; // Función de fuente de calor
}

double initialCondition(double x) {
    return sin(M_PI * x); // Condición inicial
}

int main() {
    double dx = L / (n - 1);
    double dt = T / m;
    double alpha = kappa * dt / (dx * dx);

    vector<double> u_prev(n, 0.0);
    vector<double> u(n, 0.0);

    // Inicialización de las condiciones iniciales
    for (int i = 0; i < n; ++i) {
        double x = i * dx;
        u_prev[i] = initialCondition(x);
    }

    // Iteración en el tiempo
    for (int t = 0; t < m; ++t) {
        for (int i = 1; i < n - 1; ++i) {
            u[i] = u_prev[i] + alpha * (u_prev[i - 1] - 2 * u_prev[i] + u_prev[i + 1])
                   + dt * f(i * dx, t * dt);
        }
        // Condiciones de frontera
        u[0] = u[n - 1] = 0.0;

        // Actualización de los valores
        u_prev = u;
    }

    // Salida del resultado
    for (int i = 0; i < n; ++i) {
        cout << "u(" << i * dx << ") = " << u[i] << endl;
    }

    return 0;
}
