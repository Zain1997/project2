package nl.amazingamazigh.amazigh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class About extends Activity {

    TextView header;
    TextView heading1;
    TextView text1;
    TextView heading2;
    TextView text2;
    TextView heading3;
    TextView text3;
    TextView heading4;
    TextView text4;
    TextView heading5;
    TextView text5;
    TextView text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        header = (TextView) findViewById(R.id.header);
        heading1 = (TextView) findViewById(R.id.heading1);
        text1 = (TextView) findViewById(R.id.text1);
        heading2 = (TextView) findViewById(R.id.heading2);
        text2 = (TextView) findViewById(R.id.text2);
        heading3 = (TextView) findViewById(R.id.heading3);
        text3 = (TextView) findViewById(R.id.text3);
        heading4 = (TextView) findViewById(R.id.heading4);
        text4 = (TextView) findViewById(R.id.text4);
        heading5 = (TextView) findViewById(R.id.heading5);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setLanguage(prefs.getInt("selectedLanguage", 1));
    }

    private void setLanguage(int language){
        switch (Language.getLanguageFromInt(language)) {
            case german:
                header.setText("Über Amazigh");
                heading1.setText("Ziel");
                text1.setText("Das Ziel dieser Anwendung ist es, Kinder auf spielerische Weise Amazigh Worte zu lehren. Ausgewählt für die Amazigh aus der Nähe von Nador (North Marokko).");
                heading2.setText("Entwickler");
                text2.setText("Diese Anwendung wird von Danny Wijnands , Student Application Development (ROC Leiden) programmiert. Die Idee und die Inhalte stammen von Mohamed Boukiour, Lehrer Application Development (ROC Leiden) und Projektleiter. Khalid Mourigh und Maarten Kossmann, an der Universität von Leiden, haben materielle Unterstützung angeboten. Die Medien werden von Stefan van der Lee, Student Application Development (ROC Leiden) behandelt. Die Wörter werden von Mustafa Ayned gesprochen.");
                heading3.setText("Dank");
                text3.setText("Zu den vielen Menschen, die in irgendeiner Weise beigetragen haben.");
                heading4.setText("Medien");
                text4.setText("Diese Anwendung wurde mit Public Domain Bilder und Töne erzeugt . Auf der Website finden Sie die Quellen zu finden.");
                heading5.setText("Webseite");
                text6.setText("(Im Aufbau)");
                break;
            case spanish:
                header.setText("Sobre amazigh");
                heading1.setText("Objetivo");
                text1.setText("El objetivo de esta aplicación es enseñar a los niños de una manera divertida palabras amazigh . Elegido para el amazigh desde cerca de Nador ( norte de Marruecos ).");
                heading2.setText("Desarrolladores");
                text2.setText("Esta aplicación es programada por Danny Wijnands, estudiante de desarrollo de aplicaciones ( ROC Leiden ). La idea y el contenido provienen de Mohamed Boukiour, profesor de desarrollo de aplicaciones ( ROC Leiden ) y líder del proyecto. Khalid Mourigh y Maarten Kossmann, en la Universidad de Leiden, han ofrecido apoyo sustantivo. Los medios de comunicación es manejada por Stefan van der Lee, estudiante de desarrollo de aplicaciones ( ROC Leiden ). Las palabras son habladas por Mustafa Ayned.");
                heading3.setText("Gracias");
                text3.setText("Para las muchas personas que han contribuido de ninguna manera.");
                heading4.setText("Medios");
                text4.setText("Esta aplicación se ha creado usando imágenes y sonidos de dominio público . En el sitio web encontrará las fuentes.");
                heading5.setText("Sitio web");
                text6.setText("(En construcción)");
                break;
            case french:
                header.setText("A propos de amazighe");
                heading1.setText("Objectif");
                text1.setText("L'objectif de cette application est d'enseigner aux enfants de manière ludique des mots amazighs . Choisi pour les Amazighs de près de Nador ( nord du Maroc ).");
                heading2.setText("Développeurs");
                text2.setText("Cette application est programmée par Danny Wijnands, étudiant de développement d'applications ( ROC Leiden ). L'idée et le contenu proviennent de Mohamed Boukiour, professeur de développement d'applications ( ROC Leiden ) et chef de projet. Khalid Mourigh et Maarten Kossmann, à l'Université de Leiden, ont offert un appui . Médias est géré par Stefan van der Lee, étudiant de développement d'applications ( ROC Leiden ). Les mots sont prononcés par Mustafa Ayned.");
                heading3.setText("Merci");
                text3.setText("Pour les nombreuses personnes qui ont contribué de quelque façon.");
                heading4.setText("Médias");
                text4.setText("Cette application a été créée à l'aide d'images et de sons du domaine public . Sur le site vous trouverez les sources.");
                heading5.setText("Site");
                text6.setText("(En construction)");
                break;
            case english:
                header.setText("About Amazigh");
                heading1.setText("Goal");
                text1.setText("The objective of this application is to teach children in a fun way Amazigh words. Chosen for the Amazigh from near Nador ( North Morocco).");
                heading2.setText("Developers");
                text2.setText("This application is programmed by Danny Wijnands, student Application Development (ROC Leiden). The idea and the contents come from Mohamed Boukiour, teacher Application Development (ROC Leiden) and project leader. Khalid Mourigh and Maarten Kossmann, at the University of Leiden, have offered substantive support. Media is handled by Stefan van der Lee, student Application Development (ROC Leiden). Words are spoken by Mustafa Ayned.");
                heading3.setText("Thanks");
                text3.setText("To the many people who have contributed in any way.");
                heading4.setText("Media");
                text4.setText("This application was created using Public Domain images and sounds. On the website you will find the sources.");
                heading5.setText("Website");
                text6.setText("(under construction)");
                break;
            default:
                header.setText("Over Amazigh");
                heading1.setText("Doel");
                text1.setText("Doel van deze applicatie is om kinderen op een leuke manier Amazigh woordjes te leren. Gekozen is voor het Amazigh uit de omgeving van Nador (Noordoost Marokko).");
                heading2.setText("Ontwikkelaars");
                text2.setText("Deze applicatie is geprogrammeerd door Danny Wijnands, student Applicatie Ontwikkeling (ROC Leiden). Het idee en de inhoud komen van Mohamed Boukiour, docent Applicatie Ontwikkeling (ROC Leiden) en projectleider. Khalid Mourigh en Maarten Kossmann, verbonden aan de Universiteit van Leiden, hebben inhoudelijk ondersteuning geboden. Media is verzorgd door Stefan van der Lee, student Applicatie Ontwikkeling (ROC Leiden). Woorden zijn ingesproken door Mustafa Ayned.");
                heading3.setText("Bedankt");
                text3.setText("Aan de vele mensen die op enige manier een bijdrage hebben geleverd.");
                heading4.setText("Media");
                text4.setText("Deze applicatie is gemaakt met behulp van Public Domain plaatjes en geluiden. Op de website vindt u de bronnen.");
                heading5.setText("Website");
                text6.setText("(in aanbouw)");
                break;
        }
    }

}
