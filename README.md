# 🎙️ Projeto RadioXPrime  
## Análise e Propostas de Cobertura de Rádio FM📡

## 📌 1. Definição de  RadioXPrime

**RadioXPrime** é um aplicativo de desktop feito em **JavaFX**,no qual, há a  **simulação e análise de cobertura do sinal de rádios FM**.

👨‍🔧 A RadioXPrime tem por função **ajudar emissoras de rádiosFM a visualizar até onde sua rádio alcança e qual a população atingida, sugerindo melhorias na rádio**, com base em **cálculos técnicos de engenharia de rádio** e **estimativas populacionais**.

🖥️ A interface é simples e intuitiva: você insere os dados da rádio e o sistema analisa tudo, oferecendo sugestões de investimento para melhorar o alcance.

## 🎓 2. Conceitos Envolvidos

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


## ⚙️ 3. Como Funciona o aplicativo?

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

## ▶️ 4. Como Executar o Projeto no IntelliJ IDEA

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


## 📟 Diagramas

- [Image Diagrama de Sequência](https://github.com/user-attachments/assets/b6fb89a8-1fd0-42a7-b4ae-7c43c251b0c1)

- [Image Diagrama de caso de uso](https://github.com/user-attachments/files/21026049/Diagrama.de.caso.de.uso.pdf)

- [Image Diagrama de classe](https://github.com/user-attachments/files/21026064/Diagrama.de.classe.pdf)


