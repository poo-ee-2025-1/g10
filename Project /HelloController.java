package com.example.poo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public class HelloController {
    @FXML private TextField frequenciaField;
    @FXML private TextField potenciaField;
    @FXML private TextArea resultadosAnaliseArea;
    @FXML private VBox propostasContainer;
    @FXML private TextField escolhaPropostaField;
    @FXML private TextArea propostaAceitaArea;

    static class Radio {
        private double frequenciaMHz;
        private String nome;
        private double contorno1Km;
        private double contornoProtegidoKm;
        private double potenciaKW;

        public Radio(double frequenciaMHz, String nome, double contorno1Km, double contornoProtegidoKm, double potenciaKW) {
            this.frequenciaMHz = frequenciaMHz;
            this.nome = nome;
            this.contorno1Km = contorno1Km;
            this.contornoProtegidoKm = contornoProtegidoKm;
            this.potenciaKW = potenciaKW;
        }

        public double getFrequenciaMHz() { return frequenciaMHz; }
        public String getNome() { return nome; }
        public double getContorno1Km() { return contorno1Km; }
        public double getContornoProtegidoKm() { return contornoProtegidoKm; }
        public double getPotenciaKW() { return potenciaKW; }
    }

    static class CalculadoraSinal {
        private static final double Prec = -70.0;
        private static final double CONSTANTE_DE_AJUSTE = 0.80;
        public static final double DenSP = 2500.0;

        public static double dist1(double potenciaKW, double frequenciaMHz) {
            if (potenciaKW < 0.000001) {
                return 0.0;
            }
            double P_dBm = 10 * Math.log10(potenciaKW * 1_000_000);
            double logFreq = 20 * Math.log10(frequenciaMHz);
            double exponent = (P_dBm - Prec - 32.45 - logFreq) / 20;
            return Math.pow(10, exponent) / 100;
        }

        public static double calcularContornoProtegidoAproximado(double potenciaKW, double frequenciaMHz) {
            double distanciaTeorica = CalculadoraSinal.dist1(potenciaKW, frequenciaMHz);
            return distanciaTeorica * CONSTANTE_DE_AJUSTE;
        }

        public static double calcularContornoPrimarioAproximado(double potenciaKW, double frequenciaMHz) {
            return calcularContornoProtegidoAproximado(potenciaKW, frequenciaMHz) * 0.7;
        }

        public static double calcularAreaContornoProtegido(double contornoProtegidoKm) {
            return Math.PI * Math.pow(contornoProtegidoKm, 2);
        }

        public static long estimarPopulacaoAlcancada(double areaKm2) {
            return (long) (areaKm2 * DenSP);
        }
    }

    private static final double POTENCIA_MIN_KW = 5.0;
    private static final double POTENCIA_MAX_KW_ENTRADA = 50.0;
    private static final double POTENCIA_MAX_KW_PROPOSTA = 80.0;
    private static final double VALOR_POR_OUVINTE = 1.0;
    private static final double PERCENTUAL_POPULACAO_OUVINTE = 0.05;

    private static final Map<Integer, Map<String, Long>> PRECOS_POTENCIA = new HashMap<>();

    static {
        Map<String, Long> custos10KW = new HashMap<>();
        custos10KW.put("transmissor", 910_000L);
        custos10KW.put("projetoInstalacao", 200_000L);
        PRECOS_POTENCIA.put(10, custos10KW);

        Map<String, Long> custos20KW = new HashMap<>();
        custos20KW.put("transmissor", 1_720_000L);
        custos20KW.put("projetoInstalacao", 300_000L);
        PRECOS_POTENCIA.put(20, custos20KW);

        Map<String, Long> custos30KW = new HashMap<>();
        custos30KW.put("transmissor", 2_630_000L);
        custos30KW.put("projetoInstalacao", 400_000L);
        PRECOS_POTENCIA.put(30, custos30KW);

        Map<String, Long> custos40KW = new HashMap<>();
        custos40KW.put("transmissor", 3_300_000L);
        custos40KW.put("projetoInstalacao", 500_000L);
        PRECOS_POTENCIA.put(40, custos40KW);

        Map<String, Long> custos50KW = new HashMap<>();
        custos50KW.put("transmissor", 4_400_000L);
        custos50KW.put("projetoInstalacao", 600_000L);
        PRECOS_POTENCIA.put(50, custos50KW);

        Map<String, Long> custos60KW = new HashMap<>();
        custos60KW.put("transmissor", 5_650_000L);
        custos60KW.put("projetoInstalacao", 700_000L);
        PRECOS_POTENCIA.put(60, custos60KW);

        Map<String, Long> custos80KW = new HashMap<>();
        custos80KW.put("transmissor", 8_300_000L);
        custos80KW.put("projetoInstalacao", 950_000L);
        PRECOS_POTENCIA.put(80, custos80KW);
    }

    private List<Map<String, Object>> currentProposals = new ArrayList<>();

    @FXML
    private void analisarSinal(ActionEvent event) {
        resultadosAnaliseArea.clear();
        propostasContainer.getChildren().clear();
        propostaAceitaArea.clear();
        currentProposals.clear();

        double frequenciaUsuario;
        double potenciaUsuario;

        try {
            frequenciaUsuario = Double.parseDouble(frequenciaField.getText());
            potenciaUsuario = Double.parseDouble(potenciaField.getText());

            if (frequenciaUsuario < 75.0 || frequenciaUsuario > 108.0) {
                showAlert(Alert.AlertType.ERROR, "Erro de Entrada", "Frequência inválida.", "Por favor, insira um valor entre 75 e 108 MHz.");
                return;
            }
            if (potenciaUsuario < POTENCIA_MIN_KW || potenciaUsuario > POTENCIA_MAX_KW_ENTRADA) {
                showAlert(Alert.AlertType.ERROR, "Erro de Entrada", "Potência inválida.", String.format("Por favor, insira um valor entre %.1f KW e %.1f KW.", POTENCIA_MIN_KW, POTENCIA_MAX_KW_ENTRADA));
                return;
            }

            double contornoProtegidoUsuario = CalculadoraSinal.calcularContornoProtegidoAproximado(potenciaUsuario, frequenciaUsuario);
            double areaAlcancada = CalculadoraSinal.calcularAreaContornoProtegido(contornoProtegidoUsuario);
            long populacaoAlcancada = CalculadoraSinal.estimarPopulacaoAlcancada(areaAlcancada);

            StringBuilder analiseBuilder = new StringBuilder();
            analiseBuilder.append("Análise da Sua Rádio\n");
            analiseBuilder.append(String.format("Frequência: %.1f MHz\n", frequenciaUsuario));
            analiseBuilder.append(String.format("Potência Atual: %.1f kW\n", potenciaUsuario));
            analiseBuilder.append(String.format("Contorno Protegido Atual: %.2f km\n", contornoProtegidoUsuario));
            analiseBuilder.append(String.format("Área de Cobertura Atual (Contorno Protegido): %.2f km²\n", areaAlcancada));
            analiseBuilder.append(String.format("População Potencialmente Alcançada Atualmente : Aproximadamente %d pessoas\n", populacaoAlcancada));
            resultadosAnaliseArea.setText(analiseBuilder.toString());

            currentProposals = gerarPropostasMelhoria(frequenciaUsuario, potenciaUsuario, populacaoAlcancada, contornoProtegidoUsuario);
            displayProposals(currentProposals);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Entrada", "Entrada inválida.", "Por favor, insira valores numéricos válidos para Frequência e Potência.");
        }
    }

    private void displayProposals(List<Map<String, Object>> proposals) {
        propostasContainer.getChildren().clear();
        if (proposals.isEmpty()) {
            propostasContainer.getChildren().add(new Label("Nenhuma proposta de melhoria foi gerada para sua rádio."));
            return;
        }

        String[] labels = {"Proposta de Baixo Orçamento", "Proposta de Médio Orçamento", "Proposta de Alto Orçamento"};

        for (int i = 0; i < proposals.size(); i++) {
            Map<String, Object> proposta = proposals.get(i);
            String propostaLabel = "";
            if (proposals.size() == 1) {
                propostaLabel = "Proposta Única";
            } else if (proposals.size() == 2) {
                propostaLabel = (i == 0) ? "Proposta de Baixo Orçamento" : "Proposta de Alto Orçamento";
            } else if (proposals.size() == 3) {
                propostaLabel = labels[i];
            }


            StringBuilder proposalText = new StringBuilder();
            proposalText.append(String.format("%d. %s: Aumentar para %.1f KW.\n", (i + 1), propostaLabel, (double) proposta.get("potencia")));
            proposalText.append(String.format("   - Novo Contorno Protegido: %.2f km\n", (double) proposta.get("novoContornoProtegido")));
            proposalText.append(String.format("   - Aumento no Contorno Protegido: %.2f km\n", (double) proposta.get("aumentoContorno")));
            proposalText.append(String.format("   - Nova População no alcance: Aproximadamente %d pessoas\n", (long) proposta.get("novaPopulacao")));
            proposalText.append(String.format("   - Aumento de População no alcance: Aproximadamente %d pessoas\n", (long) proposta.get("aumentoPublico")));
            proposalText.append(String.format("   - Impacto no Salário da Rádio mensalmente: R$ %,.2f\n", (double) proposta.get("impactoSalario")));

            long custoTotal = (long) proposta.get("custoTotal");
            long custoTransmissor = (long) proposta.get("custoTransmissor");
            long custoProjetoInstalacao = (long) proposta.get("custoProjetoInstalacao");

            if (custoTotal > 0) {
                proposalText.append(String.format("   - Custo Estimado Total: R$ %,d\n", custoTotal));
                proposalText.append(String.format("     (Transmissor: R$ %,d | Projeto e Instalação: R$ %,d)\n", custoTransmissor, custoProjetoInstalacao));
            } else {
                proposalText.append("   - Custo Estimado: Não disponível para esta potência.\n");
            }

            TextArea proposalTextArea = new TextArea(proposalText.toString());
            proposalTextArea.setEditable(false);
            proposalTextArea.setWrapText(true);
            proposalTextArea.setPrefHeight(180);
            propostasContainer.getChildren().add(proposalTextArea);
        }
    }

    @FXML
    private void aceitarProposta(ActionEvent event) {
        propostaAceitaArea.clear();
        if (currentProposals.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Nenhuma Proposta", "Nenhuma proposta gerada.", "Por favor, analise o sinal primeiro para gerar propostas.");
            return;
        }

        try {
            int escolha = Integer.parseInt(escolhaPropostaField.getText());
            if (escolha >= 1 && escolha <= currentProposals.size()) {
                Map<String, Object> propostaAceita = currentProposals.get(escolha - 1);

                String nomePropostaAceita = "";
                if (currentProposals.size() == 1) {
                    nomePropostaAceita = "Proposta Única";
                } else if (currentProposals.size() == 2) {
                    nomePropostaAceita = (escolha == 1) ? "Proposta de Baixo Orçamento" : "Proposta de Alto Orçamento";
                } else {
                    if (escolha == 1) nomePropostaAceita = "Proposta de Baixo Orçamento";
                    else if (escolha == 2) nomePropostaAceita = "Proposta de Médio Orçamento";
                    else nomePropostaAceita = "Proposta de Alto Orçamento";
                }

                StringBuilder acceptedProposalText = new StringBuilder();
                acceptedProposalText.append("Proposta Aceita \n");
                acceptedProposalText.append(String.format("Você escolheu a %s: Aumentar para %.1f KW.\n", nomePropostaAceita, (double) propostaAceita.get("potencia")));
                acceptedProposalText.append(String.format("   - Novo Contorno Protegido: %.2f km\n", (double) propostaAceita.get("novoContornoProtegido")));
                acceptedProposalText.append(String.format("   - Aumento no Contorno Protegido: %.2f km\n", (double) propostaAceita.get("aumentoContorno")));
                acceptedProposalText.append(String.format("   - Nova População no alcance: Aproximadamente %d pessoas\n", (long) propostaAceita.get("novaPopulacao")));
                acceptedProposalText.append(String.format("   - Aumento de População no alcance: Aproximadamente %d pessoas\n", (long) propostaAceita.get("aumentoPublico")));
                acceptedProposalText.append(String.format("   - Impacto no Salário da Rádio (mensal): R$ %,.2f\n", (double) propostaAceita.get("impactoSalario")));

                long custoTotal = (long) propostaAceita.get("custoTotal");
                long custoTransmissor = (long) propostaAceita.get("custoTransmissor");
                long custoProjetoInstalacao = (long) propostaAceita.get("custoProjetoInstalacao");

                if (custoTotal > 0) {
                    acceptedProposalText.append(String.format("   - Custo Estimado Total: R$ %,d\n", custoTotal));
                    acceptedProposalText.append(String.format("     (Transmissor: R$ %,d | Projeto e Instalação: R$ %,d)\n", custoTransmissor, custoProjetoInstalacao));
                } else {
                    acceptedProposalText.append("   - Custo Estimado: Não disponível para esta potência.\n");
                }
                acceptedProposalText.append("Parabéns pela sua escolha! Entraremos em contato para formalizar.");

                propostaAceitaArea.setText(acceptedProposalText.toString());

            } else {
                showAlert(Alert.AlertType.ERROR, "Escolha Inválida", "Número da proposta inválido.", "Por favor, digite um número de proposta válido.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Entrada Inválida", "Entrada inválida.", "Por favor, digite um número válido para a escolha da proposta.");
        }
    }

    private List<Map<String, Object>> gerarPropostasMelhoria(double frequenciaAtual, double potenciaAtual, long populacaoAtual, double contornoProtegidoAtual) {
        List<Map<String, Object>> propostasGeradasList = new ArrayList<>();

        if (potenciaAtual == 50.0) {
            double[] potenciasPropostasEspecificas = {60.0, 80.0};
            for (double propostaPotencia : potenciasPropostasEspecificas) {
                if (propostaPotencia > POTENCIA_MAX_KW_PROPOSTA) {
                    continue;
                }
                adicionarPropostaToList(propostasGeradasList, frequenciaAtual, potenciaAtual, populacaoAtual, contornoProtegidoAtual, propostaPotencia);
            }
        } else {
            double proximaPotenciaParaProposta = Math.ceil(potenciaAtual / 10.0) * 10.0;
            if (proximaPotenciaParaProposta <= potenciaAtual) {
                proximaPotenciaParaProposta += 10.0;
            }

            for (double propostaPotencia = proximaPotenciaParaProposta; propostaPotencia <= POTENCIA_MAX_KW_PROPOSTA && propostasGeradasList.size() < 3; propostaPotencia += 10.0) {
                if (propostaPotencia <= potenciaAtual) {
                    continue;
                }
                if (propostaPotencia > POTENCIA_MAX_KW_PROPOSTA) {
                    continue;
                }
                adicionarPropostaToList(propostasGeradasList, frequenciaAtual, potenciaAtual, populacaoAtual, contornoProtegidoAtual, propostaPotencia);
            }
        }

        return propostasGeradasList;
    }

    private void adicionarPropostaToList(List<Map<String, Object>> propostasList, double frequenciaAtual, double potenciaAtual, long populacaoAtual, double contornoProtegidoAtual, double propostaPotencia) {
        double novoContornoProtegido = CalculadoraSinal.calcularContornoProtegidoAproximado(propostaPotencia, frequenciaAtual);
        double novaArea = CalculadoraSinal.calcularAreaContornoProtegido(novoContornoProtegido);
        long novaPopulacao = CalculadoraSinal.estimarPopulacaoAlcancada(novaArea);
        long aumentoPublico = novaPopulacao - populacaoAtual;

        double novosOuvintes = aumentoPublico * PERCENTUAL_POPULACAO_OUVINTE;
        double impactoSalario = novosOuvintes * VALOR_POR_OUVINTE;

        Map<String, Long> custos = PRECOS_POTENCIA.getOrDefault((int) propostaPotencia, new HashMap<>());
        long custoTransmissor = custos.getOrDefault("transmissor", 0L);
        long custoProjetoInstalacao = custos.getOrDefault("projetoInstalacao", 0L);
        long custoTotal = custoTransmissor + custoProjetoInstalacao;

        double aumentoContorno = novoContornoProtegido - contornoProtegidoAtual;

        Map<String, Object> proposta = new HashMap<>();
        proposta.put("potencia", propostaPotencia);
        proposta.put("novaPopulacao", novaPopulacao);
        proposta.put("aumentoPublico", aumentoPublico);
        proposta.put("custoTotal", custoTotal);
        proposta.put("custoTransmissor", custoTransmissor);
        proposta.put("custoProjetoInstalacao", custoProjetoInstalacao);
        proposta.put("impactoSalario", impactoSalario);
        proposta.put("novoContornoProtegido", novoContornoProtegido);
        proposta.put("aumentoContorno", aumentoContorno);

        propostasList.add(proposta);
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
