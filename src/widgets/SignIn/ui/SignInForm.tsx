import { FC, useState } from "react";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";
import { Checkbox } from "~/components/ui/checkbox";
import { toast } from "sonner";
import { LoginFormProps, FormErrors } from "../model/types";
import { loginSchema, LoginFormData, validateCredentials } from "../model/schema";
import { useRouter } from "next/navigation";
import Image from "next/image";
import image from '~/shared/ui/image.png';
export const SignInForm: FC<LoginFormProps> = ({onSubmit,isLoading = false,}) => {
const router = useRouter();
  const [formData, setFormData] = useState<LoginFormData>({
    email: "",
    password: "",
    rememberMe: false,
  });

  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [submitAttempted, setSubmitAttempted] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({...prev,[name]: type === "checkbox" ? checked : value,}));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitAttempted(true);

    try {
      // Валидация с помощью Zod
      await loginSchema.parseAsync(formData);
      setFormErrors({});

      // Проверка учетных данных (эмуляция)
      const isValid = await validateCredentials(formData.email, formData.password);

      if (isValid) {
        onSubmit(formData);
        toast.success("Вход выполнен успешно");
        router.push("/ClassList")// Перенаправление на главную страницу
      } else {
        setFormErrors({
          general: "Неверный email или пароль",
        });
        toast.error("Ошибка входа", {
          description: "Неверный email или пароль",
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
    <form onSubmit={handleSubmit}  className="space-y-6 max-w-lg mx-auto">
      <div className="space-y-2">
      <div className="flex-1 flex justify-center items-center">
          <Image
            src={image}
            alt="start"
            width={500}
            height={300}
            className="w-full h-auto"/>
        </div>
        <label htmlFor="email" className="block text-sm font-medium">
          Email
        </label>
        <div className="relative">
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
        </div>
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
        <div className="relative">
          <Input
            id="password"
            name="password"
            type="text"
            placeholder="Введите пароль"
            className={`pl-10 pr-10 input-focus-effect ${
              submitAttempted && formErrors.password
                ? "border-red-500 focus:ring-red-500"
                : ""
            }`}
            value={formData.password}
            onChange={handleChange}
          />
          
        </div>
        {submitAttempted && formErrors.password && (
          <p className="text-red-500 text-xs mt-1 animate-slide-in">
            {formErrors.password}
          </p>
        )}
      </div>

      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-2">
          <Checkbox
            id="rememberMe"
            name="rememberMe"
            checked={formData.rememberMe}
            onCheckedChange={(checked) =>
              setFormData((prev) => ({ ...prev, rememberMe: !!checked }))
            }
          />
          <label
            htmlFor="rememberMe"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
            Остаться в системе
          </label>
        </div>
      </div>

      {submitAttempted && formErrors.general && (
        <div className="p-3 bg-red-50 border border-red-200 rounded text-red-500 text-sm animate-slide-in">
          {formErrors.general}
        </div>
      )}
    <div className="flex justify-center mt-6">
    <Button
        type="submit"
        className="bg-primary hover:bg-primary/90 text-white font-medium py-2 px-8"
        disabled={isLoading}>
        {isLoading ? "Вход..." : "Войти"}
      </Button>

    </div>
     
    </form>
  );
};