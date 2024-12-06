package com.universidade.sistema.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.model.TurmaDisciplina;
import com.universidade.sistema.repository.TurmaDisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    public void exportarHorarioAluno(Aluno aluno) throws IOException {
        // Caminho para o diretório onde os arquivos serão salvos
        String destinoPasta = System.getProperty("user.home") + "/Documentos/Faculdade/sexto-periodo/persistencia/horarios_alunos";

        // Criar o diretório, se não existir
        File diretorio = new File(destinoPasta);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria o diretório e subdiretórios, se necessário
        }

        // Geração de um nome de arquivo único
        String timestamp = String.valueOf(System.currentTimeMillis());
        String caminhoArquivo = destinoPasta + "/horario_aluno_" + aluno.getId() + "_" + timestamp + ".pdf";

        // Criação do PDF
        PdfWriter writer = new PdfWriter(new FileOutputStream(caminhoArquivo));
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf);

        // Título principal
        Paragraph titulo = new Paragraph("Horário do Aluno")
                .setFontSize(18)
                .setBold()
                .setMarginBottom(20);
        document.add(titulo);

        // Informações do aluno
        document.add(new Paragraph("Aluno: " + aluno.getNome()).setFontSize(12).setBold());
        document.add(new Paragraph("Matrícula: " + aluno.getMatricula()).setFontSize(12));
        document.add(new Paragraph("Turma: " + (aluno.getTurma() != null ? aluno.getTurma().getNome() : "Sem turma")).setFontSize(12));
        document.add(new Paragraph(" ")); // Espaço entre as seções

        // Buscar horários da turma do aluno
        List<TurmaDisciplina> horarios = turmaDisciplinaRepository.findByTurmaId(aluno.getTurma().getId());

        if (!horarios.isEmpty()) {
            // Adicionar título da seção de horários
            document.add(new Paragraph("Horários").setFontSize(14).setBold().setUnderline().setMarginBottom(10));

            // Exibir cada horário em formato organizado
            for (TurmaDisciplina horario : horarios) {
                document.add(new Paragraph("Disciplina: " + horario.getDisciplina().getNome())
                        .setFontSize(12).setFontColor(ColorConstants.BLUE));
                document.add(new Paragraph("Dia da Semana: " + horario.getDiaSemana()).setFontSize(12));
                document.add(new Paragraph("Horário: " + horario.getHorarioInicio() + " - " + horario.getHorarioTermino()).setFontSize(12));
                document.add(new Paragraph("-----------------------------").setFontColor(ColorConstants.GRAY).setMarginBottom(10));
            }
        } else {
            // Mensagem caso não existam horários
            document.add(new Paragraph("Nenhum horário encontrado para o aluno.")
                    .setFontSize(12)
                    .setFontColor(ColorConstants.RED));
        }

        // Rodapé
        document.add(new Paragraph("Gerado automaticamente pelo sistema de gerenciamento acadêmico.")
                .setFontSize(10)
                .setMarginTop(20)
                .setFontColor(ColorConstants.GRAY));

        document.close();
    }
}
