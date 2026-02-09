import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL,
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: (credentials) => api.post('/auth/login', credentials),
    register: (userData) => api.post('/auth/register', userData),
};

export const userService = {
    getProfile: () => api.get('/users/profile'),
};

export const transactionService = {
    getAll: () => api.get('/transactions'),
    create: (transactionData) => api.post('/transactions', transactionData),
    getByCategory: (category) => api.get(`/transactions/category/${category}`),
    getByDateRange: (start, end) => api.get(`/transactions/date-range?start=${start}&end=${end}`),
};

export const insightService = {
    getCategoryWise: () => api.get('/insights/category-wise'),
    getMonthlySummary: () => api.get('/insights/monthly-summary'),
};

export default api;
