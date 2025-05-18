import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {
  PlusIcon,
  MagnifyingGlassIcon,
  FunnelIcon,
} from "@heroicons/react/24/outline";
import type { AppState, AppDispatch } from "../../store";
import { fetchComplaints } from "../../store/slices/complaintSlice";
import { showError } from "../../store/slices/notificationSlice";

const statusColors = {
  pending: "bg-yellow-100 text-yellow-800",
  in_progress: "bg-blue-100 text-blue-800",
  resolved: "bg-green-100 text-green-800",
  rejected: "bg-gray-100 text-gray-800",
} as const;

const priorityColors = {
  low: "bg-green-100 text-green-800",
  medium: "bg-yellow-100 text-yellow-800",
  high: "bg-red-100 text-red-800",
} as const;

const Complaints = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { items: complaints, loading } = useSelector(
    (state: AppState) => state.complaints
  );
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState<string>("all");
  const [priorityFilter, setPriorityFilter] = useState<string>("all");

  useEffect(() => {
    const loadComplaints = async () => {
      try {
        await dispatch(fetchComplaints()).unwrap();
      } catch (error: unknown) {
        const errorMessage =
          error instanceof Error ? error.message : "Failed to fetch complaints";
        dispatch(showError(errorMessage));
      }
    };
    loadComplaints();
  }, [dispatch]);

  const filteredComplaints = complaints.filter((complaint) => {
    const matchesSearch =
      complaint.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      complaint.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus =
      statusFilter === "all" || complaint.status === statusFilter;
    const matchesPriority =
      priorityFilter === "all" || complaint.priority === priorityFilter;
    return matchesSearch && matchesStatus && matchesPriority;
  });

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-semibold text-gray-900">Complaints</h1>
        <button
          className="btn btn-primary flex items-center gap-2"
          onClick={() => navigate("/complaints/new")}
        >
          <PlusIcon className="h-5 w-5" />
          New Complaint
        </button>
      </div>

      <div className="card">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <MagnifyingGlassIcon className="h-5 w-5 text-gray-400" />
            </div>
            <input
              type="text"
              className="input pl-10"
              placeholder="Search complaints..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <FunnelIcon className="h-5 w-5 text-gray-400" />
            </div>
            <select
              className="input pl-10"
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              aria-label="Filter by status"
            >
              <option value="all">All Statuses</option>
              <option value="pending">Pending</option>
              <option value="in_progress">In Progress</option>
              <option value="resolved">Resolved</option>
              <option value="rejected">Rejected</option>
            </select>
          </div>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <FunnelIcon className="h-5 w-5 text-gray-400" />
            </div>
            <select
              className="input pl-10"
              value={priorityFilter}
              onChange={(e) => setPriorityFilter(e.target.value)}
              aria-label="Filter by priority"
            >
              <option value="all">All Priorities</option>
              <option value="low">Low</option>
              <option value="medium">Medium</option>
              <option value="high">High</option>
            </select>
          </div>
        </div>
      </div>

      <div className="table-container">
        <table className="table">
          <thead className="table-header">
            <tr>
              <th className="table-header-cell">ID</th>
              <th className="table-header-cell">Title</th>
              <th className="table-header-cell">Category</th>
              <th className="table-header-cell">Status</th>
              <th className="table-header-cell">Priority</th>
              <th className="table-header-cell">Created</th>
              <th className="table-header-cell">Actions</th>
            </tr>
          </thead>
          <tbody className="table-body">
            {filteredComplaints.map((complaint) => (
              <tr
                key={complaint.id}
                className="table-row cursor-pointer"
                onClick={() => navigate(`/complaints/${complaint.id}`)}
              >
                <td className="table-cell">{complaint.id}</td>
                <td className="table-cell">{complaint.title}</td>
                <td className="table-cell">{complaint.category.name}</td>
                <td className="table-cell">
                  <span
                    className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                      statusColors[complaint.status]
                    }`}
                  >
                    {complaint.status}
                  </span>
                </td>
                <td className="table-cell">
                  <span
                    className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                      priorityColors[complaint.priority]
                    }`}
                  >
                    {complaint.priority}
                  </span>
                </td>
                <td className="table-cell">
                  {new Date(complaint.createdAt).toLocaleDateString()}
                </td>
                <td className="table-cell">
                  <button
                    className="text-gray-400 hover:text-gray-500"
                    onClick={(e) => {
                      e.stopPropagation();
                      navigate(`/complaints/${complaint.id}`);
                    }}
                    aria-label="View complaint details"
                  >
                    <MagnifyingGlassIcon className="h-5 w-5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Complaints;
