import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./slices/authSlice";
import complaintReducer from "./slices/complaintSlice";
import categoryReducer from "./slices/categorySlice";
import agencyReducer from "./slices/agencySlice";
import notificationReducer from "./slices/notificationSlice";
import type { RootState } from "../types";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    complaints: complaintReducer,
    categories: categoryReducer,
    agencies: agencyReducer,
    notifications: notificationReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export type AppDispatch = typeof store.dispatch;
export type AppState = RootState;
