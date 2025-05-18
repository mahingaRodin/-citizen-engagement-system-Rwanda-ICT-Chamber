import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import { agencies } from "../../services/api";
import type { Agency, AgencyState } from "../../types";

const initialState: AgencyState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchAgencies = createAsyncThunk("agencies/fetchAll", async () => {
  const response = await agencies.getAll();
  return response.data;
});

export const createAgency = createAsyncThunk(
  "agencies/create",
  async (data: Partial<Agency>) => {
    const response = await agencies.create(data);
    return response.data;
  }
);

export const updateAgency = createAsyncThunk(
  "agencies/update",
  async ({ id, data }: { id: string; data: Partial<Agency> }) => {
    const response = await agencies.update(id, data);
    return response.data;
  }
);

export const deleteAgency = createAsyncThunk(
  "agencies/delete",
  async (id: string) => {
    await agencies.delete(id);
    return id;
  }
);

const agencySlice = createSlice({
  name: "agencies",
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch agencies
      .addCase(fetchAgencies.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        fetchAgencies.fulfilled,
        (state, action: PayloadAction<Agency[]>) => {
          state.loading = false;
          state.items = action.payload;
        }
      )
      .addCase(fetchAgencies.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to fetch agencies";
      })
      // Create agency
      .addCase(createAgency.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        createAgency.fulfilled,
        (state, action: PayloadAction<Agency>) => {
          state.loading = false;
          state.items.push(action.payload);
        }
      )
      .addCase(createAgency.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to create agency";
      })
      // Update agency
      .addCase(updateAgency.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        updateAgency.fulfilled,
        (state, action: PayloadAction<Agency>) => {
          state.loading = false;
          const index = state.items.findIndex(
            (item) => item.id === action.payload.id
          );
          if (index !== -1) {
            state.items[index] = action.payload;
          }
        }
      )
      .addCase(updateAgency.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to update agency";
      })
      // Delete agency
      .addCase(deleteAgency.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(
        deleteAgency.fulfilled,
        (state, action: PayloadAction<string>) => {
          state.loading = false;
          state.items = state.items.filter(
            (item) => item.id !== action.payload
          );
        }
      )
      .addCase(deleteAgency.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Failed to delete agency";
      });
  },
});

export const { clearError } = agencySlice.actions;
export default agencySlice.reducer;
