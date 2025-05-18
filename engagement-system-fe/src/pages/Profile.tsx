import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import type { AppState, AppDispatch } from "../store";
import { updateProfile } from "../store/slices/authSlice";
import { showSuccess, showError } from "../store/slices/notificationSlice";

const validationSchema = Yup.object().shape({
  firstName: Yup.string().required("First name is required"),
  lastName: Yup.string().required("Last name is required"),
  email: Yup.string().email("Invalid email").required("Email is required"),
  currentPassword: Yup.string().when("newPassword", {
    is: (val: string) => val?.length > 0,
    then: (schema) => schema.required("Current password is required"),
  }),
  newPassword: Yup.string().min(6, "Password must be at least 6 characters"),
  confirmPassword: Yup.string().oneOf(
    [Yup.ref("newPassword")],
    "Passwords must match"
  ),
});

const Profile = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { user, loading } = useSelector((state: AppState) => state.auth);

  const handleSubmit = async (values: any) => {
    try {
      await dispatch(updateProfile(values)).unwrap();
      dispatch(showSuccess("Profile updated successfully"));
    } catch (error: unknown) {
      const errorMessage =
        error instanceof Error ? error.message : "Failed to update profile";
      dispatch(showError(errorMessage));
    }
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold text-gray-900">Profile</h1>
      <Formik
        initialValues={{
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
          currentPassword: "",
          newPassword: "",
          confirmPassword: "",
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ errors, touched }) => (
          <Form className="mt-6 space-y-6">
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
              <div>
                <label
                  htmlFor="firstName"
                  className="block text-sm font-medium text-gray-700"
                >
                  First Name
                </label>
                <Field
                  id="firstName"
                  name="firstName"
                  type="text"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                />
                {errors.firstName && touched.firstName && (
                  <div className="mt-1 text-sm text-red-600">
                    {errors.firstName}
                  </div>
                )}
              </div>

              <div>
                <label
                  htmlFor="lastName"
                  className="block text-sm font-medium text-gray-700"
                >
                  Last Name
                </label>
                <Field
                  id="lastName"
                  name="lastName"
                  type="text"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                />
                {errors.lastName && touched.lastName && (
                  <div className="mt-1 text-sm text-red-600">
                    {errors.lastName}
                  </div>
                )}
              </div>
            </div>

            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700"
              >
                Email
              </label>
              <Field
                id="email"
                name="email"
                type="email"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
              />
              {errors.email && touched.email && (
                <div className="mt-1 text-sm text-red-600">{errors.email}</div>
              )}
            </div>

            <div className="border-t border-gray-200 pt-6">
              <h2 className="text-lg font-medium text-gray-900">
                Change Password
              </h2>
              <div className="mt-4 space-y-4">
                <div>
                  <label
                    htmlFor="currentPassword"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Current Password
                  </label>
                  <Field
                    id="currentPassword"
                    name="currentPassword"
                    type="password"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.currentPassword && touched.currentPassword && (
                    <div className="mt-1 text-sm text-red-600">
                      {errors.currentPassword}
                    </div>
                  )}
                </div>

                <div>
                  <label
                    htmlFor="newPassword"
                    className="block text-sm font-medium text-gray-700"
                  >
                    New Password
                  </label>
                  <Field
                    id="newPassword"
                    name="newPassword"
                    type="password"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.newPassword && touched.newPassword && (
                    <div className="mt-1 text-sm text-red-600">
                      {errors.newPassword}
                    </div>
                  )}
                </div>

                <div>
                  <label
                    htmlFor="confirmPassword"
                    className="block text-sm font-medium text-gray-700"
                  >
                    Confirm New Password
                  </label>
                  <Field
                    id="confirmPassword"
                    name="confirmPassword"
                    type="password"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                  />
                  {errors.confirmPassword && touched.confirmPassword && (
                    <div className="mt-1 text-sm text-red-600">
                      {errors.confirmPassword}
                    </div>
                  )}
                </div>
              </div>
            </div>

            <div>
              <button
                type="submit"
                disabled={loading}
                className="inline-flex justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 disabled:opacity-50"
              >
                {loading ? "Saving..." : "Save Changes"}
              </button>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default Profile;
