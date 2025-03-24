import { LoginFormData } from "./schema";

export interface LoginFormProps {
  onSubmit: (data: LoginFormData) => void;
  isLoading?: boolean;
}

export interface FormErrors {
  email?: string;
  password?: string;
  general?: string;
}
