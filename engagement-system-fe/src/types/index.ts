export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  role: "user" | "admin";
  createdAt: string;
  updatedAt: string;
}

export interface AuthState {
  user: User | null;
  token: string | null;
  loading: boolean;
  error: string | null;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterData {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

export interface Complaint {
  id: string;
  title: string;
  description: string;
  status: "pending" | "in_progress" | "resolved" | "rejected";
  priority: "low" | "medium" | "high";
  categoryId: string;
  agencyId: string;
  userId: string;
  createdAt: string;
  updatedAt: string;
  category?: Category;
  agency?: Agency;
  user?: User;
}

export interface Category {
  id: string;
  name: string;
  description: string;
}

export interface Agency {
  id: string;
  name: string;
  description: string;
}

export interface ComplaintState {
  items: Complaint[];
  stats: {
    total: number;
    pending: number;
    inProgress: number;
    resolved: number;
    rejected: number;
    byCategory: { [key: string]: number };
    byAgency: { [key: string]: number };
  };
  loading: boolean;
  error: string | null;
}

export interface CategoryState {
  items: Category[];
  loading: boolean;
  error: string | null;
}

export interface AgencyState {
  items: Agency[];
  loading: boolean;
  error: string | null;
}

export interface RootState {
  auth: AuthState;
  complaints: ComplaintState;
  categories: CategoryState;
  agencies: AgencyState;
  notifications: {
    notifications: Array<{
      id: string;
      type: "success" | "error" | "info" | "warning";
      message: string;
      duration?: number;
      title?: string;
      action?: {
        label: string;
        onClick: () => void;
      };
    }>;
  };
}

export interface DashboardStats {
  totalComplaints: number;
  pendingComplaints: number;
  totalCategories: number;
  totalAgencies: number;
  recentComplaints: Complaint[];
}

export interface Response {
  id: number;
  content: string;
  complaint: Complaint;
  user: User;
  createdAt: string;
  updatedAt: string;
}

export interface Attachment {
  id: number;
  fileName: string;
  fileType: string;
  fileSize: number;
  fileUrl: string;
  complaint: Complaint;
  createdAt: string;
}

export interface Notification {
  id: number;
  message: string;
  type: "COMPLAINT_STATUS" | "RESPONSE" | "SYSTEM";
  read: boolean;
  user: User;
  createdAt: string;
}

export interface ComplaintTracking {
  id: number;
  status: string;
  comment: string;
  complaint: Complaint;
  user: User;
  createdAt: string;
}

export interface ComplaintRating {
  id: number;
  rating: number;
  comment: string;
  complaint: Complaint;
  user: User;
  createdAt: string;
}

export interface ComplaintStats {
  total: number;
  pending: number;
  inProgress: number;
  resolved: number;
  rejected: number;
  byCategory: Record<string, number>;
  byAgency: Record<string, number>;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}

export interface ApiError {
  message: string;
  status: number;
  errors?: Record<string, string[]>;
}
