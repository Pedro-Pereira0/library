# WP2A - Client questions

## US07 - As Librarian, I want to register a book (isbn, title, genre, description, author(s))

**1. Gostaria de saber como o Bibliotecário(Librarian) obtém a informação do livro que gostaria de registar (isbn, title, genre, description, author(s)).**  

> O Bibliotecário já possui essa informação. Serão dados de input para este caso de uso.

**2. Quais são os critério de aceitação (acceptance criteria) da us07?**

> 7. As Librarian, I want to register a book (isbn, title, genre, description, author(s))
> * se tentar registar um livro com um ISBN já existente deve ser indicado um erro
> * ISBN usamos o formato ISBN-10 ou ISBN-13
> * titulo do livro é obrigatório e não pode comecar ou terminar em espaços
> * descrição é opcional e deve suportar conteudo HTML
> * género e autor são obrigatórios

**3. Como verificamos que um livro está disponível para ser requisitado?**

>O sistema que estão a desenvolver vai ser utilizado pelo bibliotecário que vai entregar fisicamente o livro ao leitor. o sistema nao necessita controlar o numero de exemplares de cada livro nem a sua disponibilidade

**4. Um livro pode ter mais que um género?**

>Apenas um género

**5. Em relação ao ISBN , temos de validar o digito de verificação que faz parte da norma do ISBN-10 e 13?**

>Sim

## US08 - As Librarian I want to update a book’s data

**1. Qual o dado que precisamos de introduzir para proceder à atualização dos dados de um livro?**

>À execção do ISBN todos os dados sao alteraveis

**2. Quais são os critérios de aceitação da US08?**

>Podem alterar todos os dados do livro a exceção do isbn.
>Deve ser possível “limpar” os dados não obrigatórios

## US09 - As Librarian or Reader I want to know the details of a book given its ISBN

**1. Quais são os critérios de aceitação da us09(As Librarian or Reader I want to know the details of a book given its ISBN)?**

> Devem ser mostrados todos os dados do livro (isbn, title, genre, description, author(s))

## US10 - As Librarian or Reader I want to search books by genre

**2. Quais são os critérios de aceitação da us10(. As Librarian or Reader I want to search books by genre)?**

> Devem ser mostrados todos os livros do género indicado. a pesquisa deve ser não exata, exemplo, se pesquisar por "fi" devem ser devolvidos os livros do género "ficcao" e do género "financas"