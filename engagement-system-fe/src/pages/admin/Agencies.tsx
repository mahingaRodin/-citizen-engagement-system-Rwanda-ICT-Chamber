import { useEffect, useState } from "react";
import { PlusIcon, PencilIcon, TrashIcon } from "@heroicons/react/24/outline";
import { agencies } from "../../services/api";
import type { Agency } from "../../types";

const Agencies = () => {
  const [agenciesList, setAgenciesList] = useState<Agency[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [openDialog, setOpenDialog] = useState(false);
  const [editingAgency, setEditingAgency] = useState<Agency | null>(null);
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    contactEmail: "",
    contactPhone: "",
  });

  useEffect(() => {
    fetchAgencies();
  }, []);

  const fetchAgencies = async () => {
    try {
      const response = await agencies.getAll();
      setAgenciesList(response.data);
    } catch (error) {
      console.error("Error fetching agencies:", error);
      setError("Failed to load agencies");
    } finally {
      setLoading(false);
    }
  };

  const handleOpenDialog = (agency?: Agency) => {
    if (agency) {
      setEditingAgency(agency);
      setFormData({
        name: agency.name,
        description: agency.description,
        contactEmail: agency.contactEmail,
        contactPhone: agency.contactPhone,
      });
    } else {
      setEditingAgency(null);
      setFormData({
        name: "",
        description: "",
        contactEmail: "",
        contactPhone: "",
      });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingAgency(null);
    setFormData({
      name: "",
      description: "",
      contactEmail: "",
      contactPhone: "",
    });
  };

  const handleSubmit = async () => {
    try {
      if (editingAgency) {
        await agencies.update(editingAgency.id, formData);
      } else {
        await agencies.create(formData);
      }
      handleCloseDialog();
      fetchAgencies();
    } catch (error) {
      console.error("Error saving agency:", error);
      setError("Failed to save agency");
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("Are you sure you want to delete this agency?")) {
      return;
    }

    try {
      await agencies.delete(id);
      fetchAgencies();
    } catch (error) {
      console.error("Error deleting agency:", error);
      setError("Failed to delete agency");
    }
  };

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
        <h1 className="text-2xl font-semibold text-gray-900">Agencies</h1>
        <button
          className="btn btn-primary flex items-center gap-2"
          onClick={() => handleOpenDialog()}
        >
          <PlusIcon className="h-5 w-5" />
          Add Agency
        </button>
      </div>

      {error && (
        <div className="bg-red-50 border-l-4 border-red-400 p-4">
          <div className="flex">
            <div className="flex-shrink-0">
              <svg
                className="h-5 w-5 text-red-400"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fillRule="evenodd"
                  d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                  clipRule="evenodd"
                />
              </svg>
            </div>
            <div className="ml-3">
              <p className="text-sm text-red-700">{error}</p>
            </div>
          </div>
        </div>
      )}

      <div className="table-container">
        <table className="table">
          <thead className="table-header">
            <tr>
              <th className="table-header-cell">Name</th>
              <th className="table-header-cell">Description</th>
              <th className="table-header-cell">Contact Email</th>
              <th className="table-header-cell">Contact Phone</th>
              <th className="table-header-cell">Complaints</th>
              <th className="table-header-cell text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="table-body">
            {agenciesList.map((agency) => (
              <tr key={agency.id} className="table-row">
                <td className="table-cell">{agency.name}</td>
                <td className="table-cell">{agency.description}</td>
                <td className="table-cell">{agency.contactEmail}</td>
                <td className="table-cell">{agency.contactPhone}</td>
                <td className="table-cell">{agency.complaintCount}</td>
                <td className="table-cell text-right">
                  <button
                    className="text-gray-400 hover:text-gray-500 mr-4"
                    onClick={() => handleOpenDialog(agency)}
                    aria-label="Edit agency"
                  >
                    <PencilIcon className="h-5 w-5" />
                  </button>
                  <button
                    className="text-red-400 hover:text-red-500"
                    onClick={() => handleDelete(agency.id)}
                    aria-label="Delete agency"
                  >
                    <TrashIcon className="h-5 w-5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {openDialog && (
        <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center p-4">
          <div className="bg-white rounded-lg max-w-md w-full">
            <div className="px-6 py-4 border-b border-gray-200">
              <h3 className="text-lg font-medium text-gray-900">
                {editingAgency ? "Edit Agency" : "Add Agency"}
              </h3>
            </div>
            <div className="px-6 py-4">
              <div className="space-y-4">
                <div>
                  <label
                    htmlFor="name"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Name
                  </label>
                  <input
                    type="text"
                    id="name"
                    className="input mt-1"
                    value={formData.name}
                    onChange={(e) =>
                      setFormData({ ...formData, name: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label
                    htmlFor="description"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Description
                  </label>
                  <textarea
                    id="description"
                    rows={3}
                    className="input mt-1"
                    value={formData.description}
                    onChange={(e) =>
                      setFormData({ ...formData, description: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label
                    htmlFor="contactEmail"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Contact Email
                  </label>
                  <input
                    type="email"
                    id="contactEmail"
                    className="input mt-1"
                    value={formData.contactEmail}
                    onChange={(e) =>
                      setFormData({ ...formData, contactEmail: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label
                    htmlFor="contactPhone"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Contact Phone
                  </label>
                  <input
                    type="tel"
                    id="contactPhone"
                    className="input mt-1"
                    value={formData.contactPhone}
                    onChange={(e) =>
                      setFormData({ ...formData, contactPhone: e.target.value })
                    }
                  />
                </div>
              </div>
            </div>
            <div className="px-6 py-4 border-t border-gray-200 flex justify-end space-x-3">
              <button className="btn btn-secondary" onClick={handleCloseDialog}>
                Cancel
              </button>
              <button
                className="btn btn-primary"
                onClick={handleSubmit}
                disabled={
                  !formData.name.trim() || !formData.contactEmail.trim()
                }
              >
                {editingAgency ? "Update" : "Create"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Agencies;
