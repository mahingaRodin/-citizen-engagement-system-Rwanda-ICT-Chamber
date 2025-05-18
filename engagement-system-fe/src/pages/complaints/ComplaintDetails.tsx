import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import type { AppState, AppDispatch } from "../../store";
import { fetchComplaintById } from "../../store/slices/complaintSlice";

const ComplaintDetails = () => {
  const { id } = useParams();
  const dispatch = useDispatch<AppDispatch>();
  const { selectedComplaint, loading } = useSelector(
    (state: AppState) => state.complaints
  );

  useEffect(() => {
    if (id) {
      dispatch(fetchComplaintById(id));
    }
  }, [dispatch, id]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!selectedComplaint) {
    return <div>Complaint not found</div>;
  }

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold text-gray-900">
        {selectedComplaint.title}
      </h1>
      <div className="mt-4 space-y-4">
        <p className="text-gray-600">{selectedComplaint.description}</p>
        <div className="flex items-center space-x-2">
          <span className="text-sm font-medium text-gray-500">Status:</span>
          <span
            className={`inline-flex rounded-full px-2 text-xs font-semibold leading-5 ${
              selectedComplaint.status === "pending"
                ? "bg-green-100 text-green-800"
                : selectedComplaint.status === "in_progress"
                ? "bg-yellow-100 text-yellow-800"
                : "bg-gray-100 text-gray-800"
            }`}
          >
            {selectedComplaint.status}
          </span>
        </div>
      </div>
    </div>
  );
};

export default ComplaintDetails;
