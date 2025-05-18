import axios from "axios";
import type { User, Complaint, Category, Agency } from "../types";

const api = axios.create({
  baseURL: "http://localhost:9090/mvp/api/v3",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
  withCredentials: true, // Enable credentials since backend now allows it
});

// Request interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

// Auth endpoints
export const auth = {
  register: (data: {
    name: string;
    email: string;
    password: string;
    phoneNumber: string;
  }) => api.post("/users/register", data),
  login: (data: { email: string; password: string }) =>
    api.post("/auth/login", data),
  logout: () => {
    localStorage.removeItem("token");
    window.location.href = "/login";
  },
  getProfile: () => api.get("/users/profile"),
  updateProfile: (data: {
    firstName: string;
    lastName: string;
    email: string;
    currentPassword?: string;
    newPassword?: string;
  }) => api.put("/users/profile", data),
};

// Complaints endpoints
export const complaints = {
  getAll: (params?: {
    page?: number;
    limit?: number;
    status?: string;
    priority?: string;
    search?: string;
  }) => api.get("/complaints", { params }),
  getById: (id: string) => api.get(`/complaints/${id}`),
  create: (data: Partial<Complaint>) => api.post("/complaints", data),
  update: (id: string, data: Partial<Complaint>) =>
    api.put(`/complaints/${id}`, data),
  delete: (id: string) => api.delete(`/complaints/${id}`),
  getStats: () => api.get("/complaints/stats"),
};

// Categories endpoints
export const categories = {
  getAll: () => api.get("/categories"),
  getById: (id: string) => api.get(`/categories/${id}`),
  create: (data: Partial<Category>) => api.post("/categories", data),
  update: (id: string, data: Partial<Category>) =>
    api.put(`/categories/${id}`, data),
  delete: (id: string) => api.delete(`/categories/${id}`),
};

// Agencies endpoints
export const agencies = {
  getAll: () => api.get("/agencies"),
  getById: (id: string) => api.get(`/agencies/${id}`),
  create: (data: Partial<Agency>) => api.post("/agencies", data),
  update: (id: string, data: Partial<Agency>) =>
    api.put(`/agencies/${id}`, data),
  delete: (id: string) => api.delete(`/agencies/${id}`),
};

export default api;
