import { FC } from 'react';


type PageTitle = {
  text: string
}

export const PageTitle: FC<PageTitle> = ({ text }) => {
  return (
    <div>
        <h1 className="text-3xl font-semibold text-center mb-8">
            {text}
          </h1>
    </div>
    
  );
};