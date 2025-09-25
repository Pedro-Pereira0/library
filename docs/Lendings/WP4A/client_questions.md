## US15

**1. Será possível fazer um empréstimo de vários livros a um leitor? Ou cada livro emprestado, corresponde a um só empréstimo?**  

    Um empréstimo é apenas de um único livro.

**2. O tempo limite de entrega de um livro é expectável que mude?**

    O número de dias que um livro pode ser requisitado é um valor fixo que deve ser facilmente configurável (ex: ficheiro de propriedades, bootstraping)

**3. O número de dias que um livro pode ser requisitado pode ser diferente para livro diferentes, requisitados num mesmo momento? Ou é um valor fixo para todos os livros?**

    É um valor fixo para todos os livros. A biblioteca pode a qualquer momento decidir que "de hoje em diante" o número de dias de empréstimo é diferente

**4. A associação do empréstimo ao livro pode ser feito através do isbn? (usar o isbn como id do livro)**

    O isbn é identificador do livro.

## US16

**1. Quando um utilizador pretende devolver um livro, que informação é que o sistema lhe deve pedir?**

    Deve ser dada a possibilidade de colocar um comentário/observações (opcional).

**2. A questão era no sentido de saber se o sistema pede ao reader o lending number ou o isbn para identificar o livro a devolver.**

    Deve existir as duas possibilidades 

**3. O comentário fica afeto ao livro, ou ao empréstimo?**

    o comentário é sobre o empréstimo

**4. Qual o tamanho máximo do comentário?**

    1024 caracteres

## US17

**1. Pode especificar quais são os detalhes que devem ser facultados? Existe alguma diferença nos detalhes a serem facultados, caso seja um leitor ou um bibliotecário a solicitar?**

    A ambos deve ser facultado:

    - lending number
    - book title
    - lending date
    - return date
    - number of days till return date (if applicable)
    - number of days in overdue (if applicable)
    - fine amount (if applicable)

**2. O lending number é um id auto-gerado e auto-incrementado?**

    O lending number é um número criado pelo sistema e composto pelo ano de registo e um número sequencial, ex: 2023/1, 2024/19876