'use client'
import { FC,useState } from "react";
import { SignUpForm } from "~/widgets/SignUp/ui/SignUpForm";
import { LoginFormData } from "~/widgets/SignIn/model/schema";
import { PageTitle } from "~/features/PageTitle";
import '../styles/globals.css';

const SignUp: FC = () => {
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (data: LoginFormData) => {
      setIsLoading(true);
      console.log("Форма отправлена:", data);
    };
  
    return (
        <main className="container py-10 px-4">         
            <PageTitle
              text='Введите данные аккаунта'/>
              <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-sm border">
            <SignUpForm onSubmit={handleSubmit} isLoading={isLoading} />
        </div> 
        </main> 
    );
  };
  export default SignUp