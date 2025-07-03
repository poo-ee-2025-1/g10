# 🎙️ Projeto RadioXPrime  
## Análise e Propostas de Cobertura de Rádio FM📡


## 1.🧾 Introdução

Voltado especialmente para emissoras de rádioFM, o aplicativo RADIOXPRIME , auxilia na **visualização técnica do alcance do sinal**, apoiando **decisões estratégicas e investimentos futuros** para essas rádios.

## 2.💡 Motivação

Em busca de um tema relacionado à engenharia elétrica, surgiu o interesse pela área de telecomunicações, um campo essencial para garantir comunicação eficiente, acessível e de longo alcance. Dentro desse meio, a rádio FM foi escolhida por ser de fácil acesso e presente no cotidiano das pessoas. Desse modo, o projeto RadioXPrime representa um estudo acadêmico que se utiliza dos conhecimentos adquiridos de engenharia e cálculos , juntamente com a programação orientada a objetos.


## 3.❗Descrição do Problema

- O rádio tradicional (AM/FM) vem enfrentando um declínio lento ao longo do tempo em alguns mercados e faixas etárias, especialmente com a ascensão de serviços de streaming e podcasts. Logo, esse projeto foi escolhido com foco em analisar as características dessas rádiosFM, oferecendo melhorias justamente para aumentar o público.



## 📌 4. Definição e objetivo da RadioXPrime

**RadioXPrime** é um aplicativo de desktop feito em **JavaFX** ,no qual, há a  **simulação e análise de cobertura do sinal de rádios FM**.

👨‍🔧 A RadioXPrime tem por função **auxiliar emissoras de rádiosFM a visualizar o alcance de sua rádio e qual a população atingida sugerindo melhorias na rádio**, com base em **cálculos técnicos de engenharia de rádio** e **estimativas populacionais**.

🖥️ A interface é simples e intuitiva: você insere os dados da rádio e o sistema analisa tudo, oferecendo sugestões de investimento para melhorar o alcance.

## 🎓 5. Conceitos Envolvidos

- 🎨 **Interface Gráfica (JavaFX):**  
  Uso de **FXML** .

- 📦 **Maven:**  
  Gerenciamento de dependências com o `pom.xml`.

- 📊 **Estruturas de Dados:**  
  Para listas e dados de custo por potência.

- 📶 **Cálculos (FSPL):**  
  Fórmula para estimar o alcance do sinal com base na frequência e potência.

- 👥 **Estimativas Populacionais e Probabilidades**  
  Usa conceitos de densidade demográfica e probabilidade para estimar a audiência.

- 🛡️ **Tratamento de Exceções:**  
  Uso de `try-catch` para evitar erros por entradas inválidas.


## ⚙️ 6. Como Funciona o aplicativo?

A lógica do app está dividida em partes específicas:

### 🖼️ `hello-view.fxml` (Interface)
- Campos de entrada: frequência e potência;
- Botões para "Analisar" e "Gerar Propostas";
- TextAreas para mostrar os resultados.

### 👨‍💻 `HelloController.java` (Controlador)
- **`analisarSinal()`**:  
  Valida os dados e calcula alcance, área e população.

- **`gerarPropostasMelhoria()`**:  
  Sugere propostas de baixo, médio e alto orçamento, a fim de aumentar o alcance e consequentemente ouvintes e receita.

- **`aceitarProposta()`**:  
  Mostra os detalhes da proposta escolhida pelo usuário.

### 📐 Classes Auxiliares
- **`Radio`**:  
  Guarda dados como frequência, potência e contorno.

- **`CalculadoraSinal`**:  
  Usa fórmula de perdas de energia:  
  
  d = 10^[(Pdbm - Prec - 20log(f) - 32,45):20] * 0,008

  
### 🚀 `HelloApplication.java`
- Carrega o FXML e inicia a aplicação com o título  
  **"RADIOX PRIME ltda"**

---

## ▶️ 7. Como Executar o Projeto no IntelliJ IDEA

1. ☕ Instale o **JDK 11 ou superior**;
2. 📂 Abra o projeto no **IntelliJ IDEA**;
3. 🔄 Importe as dependências Maven clicando em `Load Maven Changes`;
4. ▶️ Vá até `HelloApplication.java` e clique no botão de **Run** ---> triângulo verde;
5. 🧪 Insira frequência e potência, clique em "Analisar" e veja os resultados.

---

## ✅ Resumo Visual

| Etapa               | Ação Realizada                             |
|---------------------|--------------------------------------------|
| 📥 Inserir dados     | Frequência e Potência                      |
| 📊 Analisar sinal    | Cálculo do alcance e da população          |
| 💡 Gerar propostas   | Sugestões de melhorias com custos e ganhos |
| 📌 Aceitar proposta  | Confirmação da proposta escolhida          |


## 8. 📟 Diagramas

- [Image Diagrama de Sequência](https://github.com/user-attachments/assets/b6fb89a8-1fd0-42a7-b4ae-7c43c251b0c1)

- [Image Diagrama de caso de uso](https://github.com/user-attachments/files/21026049/Diagrama.de.caso.de.uso.pdf)

- [Image Diagrama de classe](https://github.com/user-attachments/files/21026064/Diagrama.de.classe.pdf)


## 9. 👥 Divisão de Tarefas

| Integrante           | Responsabilidades                          |
|----------------------|--------------------------------------------|
| **André Oliveira**   | 📚 Base teórica e 💻 Desenvolvimento   |
| **Gustavo de Souza** | 🧩 Criação de diagramas e 💻 Desenvolvimento |


