package com.srjheamtucozz.ufes.poo.common.widgets;

public class HelpWidget extends Widget {
    @Override
    public void show() {
        System.out.println("Usage:");
        System.out.println("    java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>"); 
    }
}