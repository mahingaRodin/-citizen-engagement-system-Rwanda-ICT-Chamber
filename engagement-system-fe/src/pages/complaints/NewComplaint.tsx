import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import type { AppState, AppDispatch } from "../../store";
import { createComplaint } from "../../store/slices/complaintSlice";
import { fetchCategories } from "../../store/slices/categorySlice";
import { fetchAgencies } from "../../store/slices/agencySlice";
import { showSuccess, showError } from "../../store/slices/notificationSlice";

const validationSchema = Yup.object().shape({
  title: Yup.string().required("Title is required"),
  description: Yup.string().required("Description is required"),
  categoryId: Yup.string().required("Category is required"),
  agencyId: Yup.string().required("Agency is required"),
});

const NewComplaint = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { items: categories, loading: categoriesLoading } = useSelector(
    (state: AppState) => state.categories
  );
  const { items: agencies, loading: agenciesLoading } = useSelector(
    (state: AppState) => state.agencies
  );

  useEffect(() => {
    const loadData = async () => {
      try {
        await Promise.all([
          dispatch(fetchCategories()).unwrap(),
          dispatch(fetchAgencies()).unwrap(),
        ]);
      } catch (error: unknown) {
        const errorMessage =
          error instanceof Error ? error.message : "Failed to load form data";
        dispatch(showError(errorMessage));
      }
    };
    loadData();
  }, [dispatch]);

  const handleSubmit = async (values: any) => {
    try {
      await dispatch(createComplaint(values)).unwrap();
      dispatch(showSuccess("Complaint created successfully"));
      navigate("/complaints");
    } catch (error: unknown) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to create complaint";
      dispatch(showError(errorMessage));
    }
  };

  if (categoriesLoading || agenciesLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold text-gray-900">New Complaint</h1>
      <Formik
        initialValues={{
          title: "",
          description: "",
          categoryId: "",
          agencyId: "",
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ errors, touched }) => (
          <Form className="mt-6 space-y-6">
            <div>
              <label
                htmlFor="title"
                className="block text-sm font-medium text-gray-700"
              >
                Title
              </label>
              <Field
                id="title"
                name="title"
                type="text"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
              {errors.title && touched.title && (
                <div className="mt-1 text-sm text-red-600">{errors.title}</div>
              )}
            </div>

            <div>
              <label
                htmlFor="description"
                className="block text-sm font-medium text-gray-700"
              >
                Description
              </label>
              <Field
                as="textarea"
                id="description"
                name="description"
                rows={4}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
              {errors.description && touched.description && (
                <div className="mt-1 text-sm text-red-600">
                  {errors.description}
                </div>
              )}
            </div>

            <div>
              <label
                htmlFor="categoryId"
                className="block text-sm font-medium text-gray-700"
              >
                Category
              </label>
              <Field
                as="select"
                id="categoryId"
                name="categoryId"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              >
                <option value="">Select a category</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                ))}
              </Field>
              {errors.categoryId && touched.categoryId && (
                <div className="mt-1 text-sm text-red-600">
                  {errors.categoryId}
                </div>
              )}
            </div>

            <div>
              <label
                htmlFor="agencyId"
                className="block text-sm font-medium text-gray-700"
              >
                Agency
              </label>
              <Field
                as="select"
                id="agencyId"
                name="agencyId"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              >
                <option value="">Select an agency</option>
                {agencies.map((agency) => (
                  <option key={agency.id} value={agency.id}>
                    {agency.name}
                  </option>
                ))}
              </Field>
              {errors.agencyId && touched.agencyId && (
                <div className="mt-1 text-sm text-red-600">
                  {errors.agencyId}
                </div>
              )}
            </div>

            <div>
              <button
                type="submit"
                className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
              >
                Submit Complaint
              </button>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default NewComplaint;
