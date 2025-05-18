import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import { complaints } from "../../services/api";
import type { Complaint, ComplaintState, ComplaintStats } from "../../types";

const initialState: ComplaintState = {
  items: [],
  selectedComplaint: null,
  stats: {
    total: 0,
    pending: 0,
    inProgress: 0,
    resolved: 0,
    rejected: 0,
    byCategory: {},
    byAgency: {},
  },
  loading: false,
  error: null,
};

export const fetchComplaints = createAsyncThunk(
  "complaints/fetchAll",
  async (params?: {
    page?: number;
    limit?: number;
    status?: string;
    priority?: string;
    search?: string;
  }) => {
    const response = await complaints.getAll(params);
    return response.data;
  }
);

export const fetchComplaintById = createAsyncThunk(
  "complaints/fetchById",
  async (id: string) => {
    const response = await complaints.getById(id);
    return response.data;
  }
);

export const fetchComplaintStats = createAsyncThunk(
  "complaints/fetchStats",
  async () => {
    const response = await complaints.getStats();
    return response.data;
  }
);

export const createComplaint = createAsyncThunk(
  "complaints/create",
  async (data: Partial<Complaint>) => {
    const response = await complaints.create(data);
    return response.data;
  }
);

export const updateComplaint = createAsyncThunk(
  "complaints/update",
  async ({ id, data }: { id: string; data: Partial<Complaint> }) => {
    const response = await complaints.update(id, data);
    return response.data;
  }
);

export const deleteComplaint = createAsyncThunk(
  "complaints/delete",
  async (id: string) => {
    await complaints.delete(id);
    return id;
  }
);

const complaintSlice = createSlice({
  name: "complaints",
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch complaints
      .addCase(fetchComplaints.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        fetchComplaints.fulfilled,
        (state, action: PayloadAction<Complaint[]>) => {
          state.loading = false;
          state.items = action.payload;
        }
      )
      .addCase(fetchComplaints.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to fetch complaints";
      })
      // Fetch single complaint
      .addCase(fetchComplaintById.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        fetchComplaintById.fulfilled,
        (state, action: PayloadAction<Complaint>) => {
          state.loading = false;
          state.selectedComplaint = action.payload;
        }
      )
      .addCase(fetchComplaintById.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to fetch complaint";
      })
      // Fetch stats
      .addCase(fetchComplaintStats.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        fetchComplaintStats.fulfilled,
        (state, action: PayloadAction<ComplaintStats>) => {
          state.loading = false;
          state.stats = action.payload;
        }
      )
      .addCase(fetchComplaintStats.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to fetch complaint stats";
      })
      // Create complaint
      .addCase(createComplaint.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        createComplaint.fulfilled,
        (state, action: PayloadAction<Complaint>) => {
          state.loading = false;
          state.items.push(action.payload);
        }
      )
      .addCase(createComplaint.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to create complaint";
      })
      // Update complaint
      .addCase(updateComplaint.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        updateComplaint.fulfilled,
        (state, action: PayloadAction<Complaint>) => {
          state.loading = false;
          const index = state.items.findIndex(
            (item) => item.id === action.payload.id
          );
          if (index !== -1) {
            state.items[index] = action.payload;
          }
          if (state.selectedComplaint?.id === action.payload.id) {
            state.selectedComplaint = action.payload;
          }
        }
      )
      .addCase(updateComplaint.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to update complaint";
      })
      // Delete complaint
      .addCase(deleteComplaint.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        deleteComplaint.fulfilled,
        (state, action: PayloadAction<string>) => {
          state.loading = false;
          state.items = state.items.filter(
            (item) => item.id !== action.payload
          );
          if (state.selectedComplaint?.id === action.payload) {
            state.selectedComplaint = null;
          }
        }
      )
      .addCase(deleteComplaint.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to delete complaint";
      });
  },
});

export const { clearError } = complaintSlice.actions;
export default complaintSlice.reducer;
