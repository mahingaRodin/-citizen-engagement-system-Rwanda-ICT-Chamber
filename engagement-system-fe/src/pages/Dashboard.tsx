import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  Assessment as AssessmentIcon,
  Report as ReportIcon,
  Category as CategoryIcon,
  Business as BusinessIcon,
} from "@mui/icons-material";
import { fetchComplaints } from "../store/slices/complaintSlice";
import { fetchComplaintStats } from "../store/slices/complaintSlice";
import type { AppState } from "../store";
import { showError } from "../store/slices/notificationSlice";
import type { AppDispatch } from "../store";
import type { Complaint } from "../types";

const Dashboard = () => {
  const dispatch = useDispatch<AppDispatch>();
  const {
    items: complaints,
    stats: complaintStats,
    loading,
  } = useSelector((state: AppState) => state.complaints);

  useEffect(() => {
    const loadData = async () => {
      try {
        await Promise.all([
          dispatch(fetchComplaints()).unwrap(),
          dispatch(fetchComplaintStats()).unwrap(),
        ]);
      } catch (error: unknown) {
        const errorMessage =
          error instanceof Error
            ? error.message
            : "Failed to load dashboard data";
        dispatch(showError(errorMessage));
      }
    };

    loadData();
  }, [dispatch]);

  if (loading) {
    return (
      <div className="flex h-full items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  const dashboardStats = [
    {
      title: "Total Complaints",
      value: complaintStats?.total.toString() || "0",
      icon: <ReportIcon sx={{ fontSize: 40, color: "primary.main" }} />,
    },
    {
      title: "Pending Complaints",
      value: complaintStats?.pending.toString() || "0",
      icon: <AssessmentIcon sx={{ fontSize: 40, color: "warning.main" }} />,
    },
    {
      title: "Categories",
      value: Object.keys(complaintStats?.byCategory || {}).length.toString(),
      icon: <CategoryIcon sx={{ fontSize: 40, color: "success.main" }} />,
    },
    {
      title: "Agencies",
      value: Object.keys(complaintStats?.byAgency || {}).length.toString(),
      icon: <BusinessIcon sx={{ fontSize: 40, color: "info.main" }} />,
    },
  ];

  return (
    <div className="space-y-6 p-6">
      <h1 className="text-2xl font-semibold text-gray-900">Dashboard</h1>

      {/* Stats Overview */}
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
        {dashboardStats.map((stat) => (
          <div key={stat.title} className="rounded-lg bg-white p-6 shadow">
            <h3 className="text-sm font-medium text-gray-500">{stat.title}</h3>
            <div className="mt-2 flex items-center justify-between">
              <p className="text-3xl font-semibold text-gray-900">
                {stat.value}
              </p>
              {stat.icon}
            </div>
          </div>
        ))}
      </div>

      {/* Recent Complaints */}
      <div className="rounded-lg bg-white shadow">
        <div className="px-6 py-4">
          <h2 className="text-lg font-medium text-gray-900">
            Recent Complaints
          </h2>
        </div>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                  ID
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                  Title
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                  Status
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                  Created
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200 bg-white">
              {complaints.slice(0, 5).map((complaint: Complaint) => (
                <tr key={complaint.id}>
                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-500">
                    {complaint.id}
                  </td>
                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-900">
                    {complaint.title}
                  </td>
                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-500">
                    <span
                      className={`inline-flex rounded-full px-2 text-xs font-semibold leading-5 ${
                        complaint.status === "pending"
                          ? "bg-green-100 text-green-800"
                          : complaint.status === "in_progress"
                          ? "bg-yellow-100 text-yellow-800"
                          : "bg-gray-100 text-gray-800"
                      }`}
                    >
                      {complaint.status}
                    </span>
                  </td>
                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-500">
                    {new Date(complaint.createdAt).toLocaleDateString()}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
