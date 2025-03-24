import { FC, useState } from "react";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";
import { toast } from "sonner";
import { RegistrationFormProps, FormErrors } from "../model/types";
import { registrationSchema, RegistrationFormData, registerUser } from "../model/schema";
import { useRouter } from "next/navigation";
import Image from "next/image";
import image from '~/shared/ui/image.png';

export const SignUpForm: FC<RegistrationFormProps> = ({ onSubmit, isLoading = false }) => {
  const router = useRouter();

  const [formData, setFormData] = useState<RegistrationFormData>({
    firstName: "",
    lastName: "",
    patronymic: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [submitAttempted, setSubmitAttempted] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitAttempted(true);

    try {
      // Валидация с помощью Zod
      await registrationSchema.parseAsync(formData);
      setFormErrors({});

      // Эмуляция регистрации пользователя
      const isRegistered = await registerUser(formData);

      if (isRegistered) {
        onSubmit(formData);
        toast.success("Регистрация успешно завершена");
        router.push("/ClassList"); // Перенаправление на главную страницу
      } else {
        setFormErrors({
          general: "Ошибка при регистрации",
        });
        toast.error("Ошибка регистрации", {
          description: "Произошла ошибка при регистрации аккаунта",
        });
      }
    } catch (error: any) {
      const errors: FormErrors = {};

      if (error.issues) {
        error.issues.forEach((issue: any) => {
          errors[issue.path[0] as keyof FormErrors] = issue.message;
        });
      }

      setFormErrors(errors);
      toast.error("Ошибка валидации", {
        description: Object.values(errors).join(", "),
      });
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6 max-w-lg mx-auto">
      
      <div className="space-y-2">
        <label htmlFor="firstName" className="block text-sm font-medium">
          Имя
        </label>
        <Input
          id="firstName"
          name="firstName"
          type="text"
          placeholder="Введите имя"
          className={`pl-10 input-focus-effect ${
            submitAttempted && formErrors.firstName
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.firstName}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.firstName && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.firstName}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <label htmlFor="lastName" className="block text-sm font-medium">
          Фамилия
        </label>
        <Input
          id="lastName"
          name="lastName"
          type="text"
          placeholder="Введите фамилию"
          className={`pl-10 input-focus-effect ${
            submitAttempted && formErrors.lastName
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.lastName}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.lastName && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.lastName}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <label htmlFor="patronymic" className="block text-sm font-medium">
          Отчество
        </label>
        <Input
          id="patronymic"
          name="patronymic"
          type="text"
          placeholder="Введите отчество"
          className={`pl-10 input-focus-effect ${
            submitAttempted && formErrors.patronymic
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.patronymic}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.patronymic && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.patronymic}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <label htmlFor="email" className="block text-sm font-medium">
          Email
        </label>
        <Input
          id="email"
          name="email"
          type="text"
          placeholder="Введите почту"
          className={`pl-10 input-focus-effect ${
            submitAttempted && formErrors.email
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.email}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.email && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.email}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <label htmlFor="password" className="block text-sm font-medium">
          Пароль
        </label>
        <Input
          id="password"
          name="password"
          type="password"
          placeholder="Введите пароль"
          className={`pl-10 pr-10 input-focus-effect ${
            submitAttempted && formErrors.password
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.password}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.password && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.password}
          </p>
        )}
      </div>

      <div className="space-y-2">
        <label htmlFor="confirmPassword" className="block text-sm font-medium">
          Повторите пароль
        </label>
        <Input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          placeholder="Подтвердите пароль"
          className={`pl-10 pr-10 input-focus-effect ${
            submitAttempted && formErrors.confirmPassword
              ? "border-red-500 focus:ring-red-500"
              : ""
          }`}
          value={formData.confirmPassword}
          onChange={handleChange}
        />
        {submitAttempted && formErrors.confirmPassword && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.confirmPassword}
          </p>
        )}
      </div>

      <div className="flex justify-center mt-6">
        <Button
          type="submit"
          className="bg-primary hover:bg-primary/90 text-white font-medium py-2 px-8"
          disabled={isLoading}
        >
          {isLoading ? "Регистрация..." : "Создать аккаунт"}
        </Button>
      </div>

    </form>
  );
};