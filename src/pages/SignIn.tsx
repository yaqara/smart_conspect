'use client'
import { FC,useState } from "react";
import { SignInForm } from "~/widgets/SignIn/ui/SignInForm";
import { LoginFormData } from "~/widgets/SignIn/model/schema";
import { PageTitle } from "~/features/PageTitle";
import '../styles/globals.css';

const SignIn: FC = () => {
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (data: LoginFormData) => {
      setIsLoading(true);
      console.log("Форма отправлена:", data);
    };
  
    return (
        <main className="container py-10 px-4">         
            <PageTitle
              text='Вход в аккаунт'/>
              <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-sm border">
            <SignInForm onSubmit={handleSubmit} isLoading={isLoading} />
        </div> 
        </main> 
    );
  };
  export default SignIn