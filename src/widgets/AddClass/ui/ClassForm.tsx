'use client'
import  { FC,useState } from "react";
import { toast } from "sonner";
import { Button } from "~/components/ui/button";
import { Input } from "~/components/ui/input";
import { classSchema, ClassFormData } from "../model/schema";
import { formFields } from "../model/types"; // Убран неиспользуемый импорт FormField

interface ClassFormProps {
  onSubmit?: (data: ClassFormData) => void;
  defaultValues?: Partial<ClassFormData>;
  isLoading?: boolean;
}

export const ClassForm: FC<ClassFormProps> = ({
  onSubmit,
  defaultValues,
  isLoading = false,
}) => {
  const [formValues, setFormValues] = useState<ClassFormData>({
    subject: defaultValues?.subject ?? "",
    number: defaultValues?.number ?? "", // Убедились, что это строка
    letter: defaultValues?.letter ?? "",
  });

  const [formErrors, setFormErrors] = useState<Record<string, string>>({});
  const [submitAttempted, setSubmitAttempted] = useState(false);

  const handleFieldChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: keyof ClassFormData
  ) => {
    setFormValues({
      ...formValues,
      [field]: e.target.value, // Все поля остаются строками
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitAttempted(true);

    try {
      await classSchema.parseAsync(formValues);
      setFormErrors({});
      onSubmit?.(formValues);
      toast.success("Класс успешно создан!");
    } catch (error: any) {
      const errors: Record<string, string> = {};
      error.issues.forEach((issue: any) => {
        errors[issue.path[0]] = issue.message;
      });
      setFormErrors(errors);
      toast.error("Ошибка валидации", {
        description: Object.values(errors).join(", "),
      });
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto">
      <div className="p-6">
        <form onSubmit={handleSubmit} className="space-y-6 max-w-lg mx-auto">
          {formFields.map((field) => (
            <div key={field.id}>
              <label className="block text-sm font-medium mb-2">
                {field.label}
              </label>
              <Input
                id={field.id}
                name={field.name}
                type={field.type}
                placeholder={field.placeholder}
                value={formValues[field.name]}
                onChange={(e) => handleFieldChange(e, field.name)}
                className={`
                  mt-1
                  ${submitAttempted && formErrors[field.name] ? "border-red-500" : ""}
                `}
              />
              {submitAttempted && formErrors[field.name] && (
                <p className="text-red-500 text-xs mt-1">
                  {formErrors[field.name]}
                </p>
              )}
            </div>
          ))}
          <div className="flex justify-center mt-6">
            <Button
              type="submit"
              className="bg-primary hover:bg-primary/90 text-white font-medium py-2 px-8 w-full"
              disabled={isLoading}
            >
              {isLoading ? "Создание..." : "Создать класс"}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};