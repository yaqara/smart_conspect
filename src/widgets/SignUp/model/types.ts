import { RegistrationFormData } from "./schema";

export interface RegistrationFormProps {
  onSubmit: (data: RegistrationFormData) => void;
  isLoading?: boolean;
}

export interface FormErrors {
  firstName?: string;
  lastName?: string;
  patronymic?: string;
  email?: string;
  password?: string;
  confirmPassword?: string;
  general?: string;
}