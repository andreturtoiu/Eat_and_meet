package com.example.andreea.eat_and_meet;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Michele on 03/02/2018.
 */

public class EventFactory {
    //Pattern Design Singleton
    private static EventFactory singleton;
    private String connectionString;
    private Event partialEvent;

    public static EventFactory getInstance() {
        if (singleton == null) {
            singleton = new EventFactory();
        }
        return singleton;
    }

    private ArrayList<Event> listaEventi = new ArrayList<Event>();

    public Event getEventById(int id){
        for( Event e : listaEventi)
            if (e.getId() == id)
                return e;
        return null;
    }



    public void editEvent(Event evento){
        int id = evento.getId();
        for(Event e:listaEventi)
            if (e.getId() == id){
                e.setDescrizione(evento.getDescrizione());
                e.setFotoList(evento.getFotoList());
                e.setFotoUriList(evento.getFotoUriList());
                e.setData(evento.getDataCalendar());
                e.setLocation(evento.getLocation());
                Notifications n= new Notifications(e.getUser(), id, Notifications.MODIFICA);
                PersonFactory.getInstance().sendNotifications(n, e);
            }
    }

    public void deleteEventById(int id){
        Event position = null;
        for(Event e:this.listaEventi){
            if (e.getId() == id ) {
                position = e;

                Notifications n= new Notifications(e.getUser(), id, Notifications.CANCELLAZIONE, e.getTitolo());
                PersonFactory.getInstance().sendNotifications(n, e);
            }
        }


        this.listaEventi.remove(position);
    }

    public void unSubscribeFromEvent(int idEvent,String idUser){
        for(Event e:listaEventi){
            if (e.getId()==idEvent){
                e.unSubscribe(idUser);
                Notifications n= new Notifications(idUser, e.getId(), Notifications.RINUNCIA);
                PersonFactory.getInstance().getUserByEmail(e.getUser()).getMyNotifications().add(n);
            }
        }
    }

    public void SubscribeToEvent(int idEvent,String idUser){
        for(Event e:listaEventi){
            if (e.getId()==idEvent){
                e.Subscribe(idUser);
            }
        }
    }

    public void RequestSubscribeToEvent(int idEvent,String idUser){
        for(Event e:listaEventi){
            if (e.getId()==idEvent){
                e.addRequest(idUser);
                Notifications n= new Notifications(idUser, e.getId(), Notifications.RICHIESTA);
                PersonFactory.getInstance().getUserByEmail(e.getUser()).getMyNotifications().add(n);

            }
        }
    }

    public boolean isEventFull(int idEvent){
        for (Event e:listaEventi){
            if (e.getId()==idEvent){
                e.isFull();
            }
        }
        return true;
    }

    public ArrayList<Event> getEventList(){
        return this.listaEventi;
    }

    public ArrayList<Event> getEventsByUser(String userID){
        ArrayList<Event> l = new ArrayList<>();
        for(Event e:listaEventi){
            if (e.getUser().equals(userID)){
                l.add(e);
            }
        }
        return l;
    }

    public ArrayList<Event> getBookingsByUser(String userID){
        ArrayList<Event> l = new ArrayList<>();
        for(Event e:listaEventi){
            if (e.isBooked(userID)){
                l.add(e);
            }
        }
        return l;
    }

    public ArrayList<Event> getPendingRequestsByUser(String userID){
        ArrayList<Event> l = new ArrayList<>();
        for(Event e:listaEventi){
            if (e.hasRequest(userID)){
                l.add(e);
            }
        }
        return l;
    }

    public Event isDateReserved(Event e,String email){

        int day = e.getDataCalendar().get(Calendar.DAY_OF_MONTH);
        int month = e.getDataCalendar().get(Calendar.MONTH);
        int year = e.getDataCalendar().get(Calendar.YEAR);
        //Verifico che l'utente non abbia eventi in quella data per pranzo_cena
        for(Event ev:this.listaEventi){
            int d = ev.getDataCalendar().get(Calendar.DAY_OF_MONTH);
            int m = ev.getDataCalendar().get(Calendar.MONTH);
            int y = ev.getDataCalendar().get(Calendar.YEAR);
            //Controllo se sono nello stesso giorno
            if(day == d && month == m && year == y && e.getPranzo_cena() == ev.getPranzo_cena() && e.getId() != ev.getId()){
                //Controllo l'organizzatore
                if (ev.getUser().equals(email)) return ev;
                //Controllo i partecipanti
                if (ev.getPartecipanti().contains(email)) return ev;
            }
        }

        return null;
    }

    public ArrayList<Event> searchEventsByFilter(String cucina,String citta,int pranzo_cena){
        ArrayList<Event> filter_cucina = new ArrayList<Event>();
        ArrayList<Event> filter_citta = new ArrayList<Event>();
        ArrayList<Event> filter_pranzo_cena = new ArrayList<Event>();
        ArrayList<Event> filter_free = new ArrayList<Event>();
        //Filtro per cucina;
        if(cucina != null)
            for(Event e:listaEventi)
                if (e.getCucina().equals(cucina) || cucina.equals("Nessuna Preferenza"))
                    filter_cucina.add(e);
        //Filtro citta
        if(citta != null){
            if(citta.equals(""))
                citta = "Roma"; //citta = PersonFactory.getIstance().citta utente loggato

                String appc = citta.toLowerCase();
                String c1 = upperCaseFirst(appc);

                for(Event e:filter_cucina){
                    if (e.getCity().contains(citta) || e.getCity().contains(c1))
                        filter_citta.add(e);}
        }

        //Filtro pranzo_cena
        for(Event e:filter_citta)
            if (e.getPranzo_cena()==pranzo_cena || pranzo_cena == -1)
                filter_pranzo_cena.add(e);
        //Filtro liberi e almeno due giorni
        long giorni;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,2);
        for(Event e:filter_pranzo_cena){
            if(!e.isFull() && c.before(e.getDataCalendar()))
                filter_free.add(e);
        }
        //Filtro miei
        ArrayList<Event> filter_final = new ArrayList<Event>();
        String loggedUser = PersonFactory.getInstance().getLoggedUser();
        for(Event e:filter_free)
            if(!loggedUser.equals(e.getUser()) && !e.isBooked(loggedUser) && !e.hasRequest(loggedUser))
                filter_final.add(e);

        //Ritorno selezione
        return filter_final;

    }

    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    public LinearLayout newEventView (Event e, View.OnClickListener ocl, Context c){

        Person user = PersonFactory.getInstance().getUserByEmail(e.getUser());
        String userMail = PersonFactory.getInstance().getLoggedUser();
        LinearLayout eventView;

        //Miei eventi
        if(user.getEmail().equals(userMail)){
         eventView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_my_events, null);
            TextView title = (TextView) eventView.findViewById(R.id.template_event_title);
            TextView info = (TextView) eventView.findViewById(R.id.template_event_info);
            ImageView photo = (ImageView) eventView.findViewById(R.id.template_event_photo);
            title.setText(e.getTitolo());
            title.setId(View.generateViewId());
            info.setText(e.getDescrizione());
            info.setId(View.generateViewId());
            if(e.getFotoList() != null && e.getFotoList().size()>0)
                photo.setImageResource(e.getFotoList().get(0));
            else if (e.getFotoUriList() != null && e.getFotoUriList().size()>0)
                photo.setImageBitmap(e.getFotoUriList().get(0).getBitmap());
            else
                photo.setImageResource(R.drawable.logo_2);
            photo.setId(View.generateViewId());
            eventView.setId(View.generateViewId());


        }else {

             eventView = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.template_event, null);

            TextView title = (TextView) eventView.findViewById(R.id.template_event_title);
            ImageView photo = (ImageView) eventView.findViewById(R.id.template_event_photo);
            ImageView userPhoto = (ImageView) eventView.findViewById(R.id.userPhoto) ;
            TextView presentation = (TextView) eventView.findViewById(R.id.titleEvent);
            userPhoto.setImageResource(user.getFoto());
            presentation.setText(user.getName() + " " + "organizza l'evento");
            title.setText(e.getTitolo());
            title.setId(View.generateViewId());
            if(e.getFotoList() != null && e.getFotoList().size()>0)
                photo.setImageResource(e.getFotoList().get(0));
            else if (e.getFotoUriList() != null && e.getFotoUriList().size()>0)
                photo.setImageBitmap(e.getFotoUriList().get(0).getBitmap());
            else
                photo.setImageResource(R.drawable.logo_2);
            photo.setId(View.generateViewId());
            eventView.setId(View.generateViewId());

            userPhoto.setId(View.generateViewId());
            presentation.setId(View.generateViewId());

        }
        //Aggiungo listener
        eventView.setOnClickListener(ocl);
        return eventView;
    }



    private EventFactory() {
        //via sicilia
        ArrayList<Double> position = new ArrayList<>(2);
        position.add(39.245459);
        position.add(9.189124);

        //Via roma 8 cagliari
        ArrayList<Double> position2 = new ArrayList<>(2);
        position2.add(39.215983);
        position2.add(9.109543);

        //Via Cagliari 78 quartu
        ArrayList<Double> position3 = new ArrayList<>(2);
        position3.add(39.245065);
        position3.add(9.184190);

        //via silvio pellico 18 quartu
        ArrayList<Double> position4 = new ArrayList<>(2);
        position4.add(39.236895);
        position4.add(9.189380);

        Event e;
        Calendar c = Calendar.getInstance();

        //Evento 0
        e = new Event();
        e.setUser("dcont@ium.it");
        e.setTitolo("Serata Fritto Misto");
        e.setDescrizione("Tagliere della Burla con salame, lardo, peperoni in bagna cauda, lonzino tonnato,\n" +
                "frittata rognosa e gnocco fritto\n" +
                "\n" +
                "Fritto misto della burla;\n" +
                "\n" +
                "Impanati: Cervella, milanese di maiale, cotechino e Grive (polpette di fegato e salsiccia)\n" +
                "\n" +
                "Al burro: fettina di fegato, salsiccetta e carote\n" +
                "\n" +
                "Il dolce: mela, amaretto, semolino  e ananas in pastella\n" +
                "\n" +
                "Bunet,\n" +
                "\n" +
                "Semifreddo al torroncino con salsa al cioccolato fondente\n" +
                "\n" +
                "Acqua, vino della casa e caffè");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Sicilia 27");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,1,20,0);
        e.setData(c);
        e.addPartecipante("rmam@ium.it");
        e.addPartecipante("atur@ium.it");
        e.addRequest("msta@ium.it");
        e.addFoto(R.drawable.frittomisto);
        e.setMaxBookings(4);
        e.setLocation(position);
        listaEventi.add(e);

        //Evento 1
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("dcont@ium.it");
        e.setTitolo("Conoscersi a tavola");
        e.setDescrizione("MENU\n" +
                "Aperitivo con salumi della Lomellina\n" +
                "Spaghetti guanciale e trevisano\n" +
                "Cotolette alla Bolognese\n" +
                "Ofelle di Parona con vin Santo\n" +
                "Una bottiglia di vino ogni 4 persone\n" +
                "Acqua \n" +
                "Caffe");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Sicilia 27");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,2,19,0);
        e.setData(c);
        e.addFoto(R.drawable.spaghetti);
        e.addRequest("ajon@ium.it");
        e.setMaxBookings(4);
        e.setLocation(position);
        listaEventi.add(e);

        //Evento 2
        e = new Event();
        e.setLocation(position);
        c = Calendar.getInstance();
        e.setUser("dcont@ium.it");
        e.setTitolo("Serata della Bourguignonne");
        e.setDescrizione("Flute di Prosecco di Valdobbiadene\n" +
                "\n" +
                "Tagliere della Burla con gnocco fritto\n" +
                "\n" +
                "Bourguignonne delle nostre carni:\n" +
                "\n" +
                "Fesa di bovino Fassone Piemontese, filetto di maiale e salsiccia accompagnate da maionese, maionese all’aglio, salsa tartara, salsa rosa, senape, ketchup e come contorno patatine fritte\n" +
                "\n" +
                "Dolce a scelta\n" +
                "\n" +
                "Acqua, vino della casa e caffè");
        e.setCucina("Francese");
        e.setPranzo_cena(Event.PRANZO);
        e.setVia("Via Sicilia 27");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,3,12,0);
        e.setData(c);
        e.addPartecipante("atur@ium.it");
        e.addPartecipante("msta@ium.it");
        e.addFoto(R.drawable.bourguignonne);
        e.setMaxBookings(4);
        listaEventi.add(e);

        //Evento 3
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("dcont@ium.it");
        e.setTitolo("Carne...carne e ancora carne");
        e.setDescrizione("MENU\n" +
                "Aperitivo di benvenuto\n" +
                "Grigliata di carne (le carni saranno scelte in funzione dei tagli che reputerò migliori dal produttore)\n" +
                "Insalata \n" +
                "Tagliere di formaggi di capra con marmellate fatte in casa\n" +
                "Offelle di Parona\n" +
                "Una bottiglia di vino ogni 4 persone\n" +
                "Caffè");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setLocation(position);
        e.setVia("Via Sicilia 27");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,4,19,0);
        e.setData(c);
        e.addFoto(R.drawable.carne);
        e.setMaxBookings(4);
        listaEventi.add(e);

        //Evento 4


        e = new Event();
        c = Calendar.getInstance();
        e.setUser("dcont@ium.it");
        e.setTitolo("A cena dai nativi americani");
        e.setDescrizione("MENU\n" +
                "Pane fritto Navajo\n" +
                "\n" +
                "Succotash mohegan\n" +
                "\n" +
                "Galletto ripieno di chicchi d'uva e mele cotta in clay pot\n" +
                "Hoe cake (polenta di mais al forno) con salsa Chimichurry\n" +
                "Budino Abenaki\n" +
                "Tisana alla liquerizia o\n" +
                "caffee della napoletana o in cialda\n" +
                "Vino rosso Nero di Troia DOC (1/3 di bottiglia a testa)\n" +
                "Acqua minerale");
        e.setCucina("Americana");
        e.setPranzo_cena(Event.PRANZO);
        e.setVia("Via Sicilia 27");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,5,12,0);
        e.setData(c);
        e.addPartecipante("rmam@ium.it");
        e.addPartecipante("msta@ium.it");
        e.addFoto(R.drawable.hoecake);
        e.setMaxBookings(4);
        e.setLocation(position);
        listaEventi.add(e);

        //Evento 5
        e = new Event();
        e.setLocation(position2);
        c = Calendar.getInstance();
        e.setUser("rmam@ium.it");
        e.setTitolo("Cena Jack Daniel");
        e.setDescrizione("Una sera in stile e sapore americano, inizieremo con pannocchie mais dolci al forno, per proseguire polenta croccante e pancetta per continuare con un hamburger 180 gr e patatine fritte per concludere con cheesecake New York e Jack Daniel’s.\nMENU\n" +
                "1) Mais dolce al forno e frittatine \n" +
                "2) Polenta croccante e pancetta\n" +
                "3) Hamburger e patatine \n" +
                "4) Cheesecake\n" +
                "Bevande comprese birra o vino, oltre al Jack Deniel’s ghiacciato");
        e.setCucina("Americana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Roma, 8");
        e.setCity("Cagliari");
        c.set(2018,3,6,19,0);
        e.setData(c);
        e.addRequest("dcont@ium.it");
        e.addFoto(R.drawable.cenajack);
        e.setMaxBookings(4);
        listaEventi.add(e);


        //Evento 6
        e = new Event();
        c = Calendar.getInstance();
        e.setLocation(position2);
        e.setUser("rmam@ium.it");
        e.setTitolo("Pranzo Sapore di Mare");
        e.setDescrizione("Tartare di gamberi crudi e cotti con avocado, punte di asparagi, chantilly al limone e crema di basilico\n" +
                "\n" +
                "Polipetti in umido con calamaro ripieno al cous cous, vongole e bottarga\n" +
                "\n" +
                "Riso Carnaroli con gallinelle di mare, seppioline, cozze, fiori di zucca e zafferano\n" +
                "\n" +
                "Tagliata di tonno al sesamo con salsa di yogurt alle erbe, misticanza e ribes\n" +
                "\n" +
                "Cazpacho di fragole e mirtilli con gelato alla crema e panna montata\n" +
                "\n" +
                "Acqua e caffè");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.PRANZO);
        e.setVia("Via Roma, 8");
        e.setCity("Cagliari");
        c.set(2018,3,7,12,30);
        e.setData(c);
        e.addPartecipante("dcont@ium.it");
        e.addPartecipante("atur@ium.it");
        e.addPartecipante("msta@ium.it");
        e.addRequest("ajon@ium.it");
        e.addFoto(R.drawable.calamari);
        e.setMaxBookings(4);
        listaEventi.add(e);

        //Evento 7
        e = new Event();
        c = Calendar.getInstance();
        e.setLocation(position2);
        e.setUser("rmam@ium.it");
        e.setTitolo("Panzerotti With Us");
        e.setDescrizione("MENU\n" +
                "Il menù prevede 4 varietà di panzerotti serviti a centro tavola, sono garantiti per ogni gnammers almeno 5 panzerotti a testa.\n" +
                "\n" +
                "I panzerotti proposti sono:\n" +
                "1) Panzerotti di Pomodoro e mozzarella;\n" +
                "2) Panzerotti di Cipolle\n" +
                "3) Panzerotti con Tonno e capperi (+ pomodoro e mozzarella)\n" +
                "4) Panzerotti con cime di rapa e salsiccia (+ peperoncino)*\n" +
                "\n" +
                "* anche senza salsiccia per i vegetariani previa comunicazione in fase di prenotazione.\n" +
                "\n" +
                "Altri ingredienti utilizzati: Origano e formaggio di tipo Pecorino.\n" +
                "\n" +
                "Bevande: Birra Peroni Gran riserve e acqua.\n" +
                "\n" +
                "Nota: l'impasto è con lievito madre e farina senatore cappelli\n" +
                "\n" +
                "Dolce: Sporcamuss ( dolce tipico pugliese di Sfoglia e crema pasticcera)");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.PRANZO);
        e.setVia("Via Roma, 8");
        e.setCity("Cagliari");
        c.set(2018,3,8,13,0);
        e.setData(c);
        e.addFoto(R.drawable.panzerotti);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 8

        e = new Event();
        c = Calendar.getInstance();
        e.setLocation(position3);
        e.setUser("atur@ium.it");
        e.setTitolo("Food & Love");
        e.setDescrizione("MENU\n" +
                "Zuppa delicatissima alle cipolle con Pancetta croccante\n" +
                "Tagliatelle fresche di farina bio ai carciofi\n" +
                "Maiale sfilacciato e insalata di cavolo cappuccio croccante\n" +
                "Tarte Tatin\n" +
                "Vino Bianco e rosso bio, pane fatto in casa da pasta madre, acqua della fonte di Principessa, caffè della moka. .");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        c.set(2018,3,9,20,15);
        e.setData(c);
        e.addFoto(R.drawable.zuppa);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 9
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("atur@ium.it");
        e.setTitolo("Sapori del Brasile");
        e.setDescrizione("MENU\n" +
                "Si inizierà con un antipasto di pão de queijo (pane fatto con amido di manioca e parmigiano) e manioca fritta.\n" +
                "\n" +
                "Piatto unico composto da fagioli neri, riso bianco e picanha brasiliana, il tutto cucinato secondo la tradizione carioca.\n" +
                "\n" +
                "Accompagnamenti di contorno, farofa (simile a un pan grattato cucinato con pancetta e salsiccia) e vinagrete (insalatina di pomodori, peperoni e cipolle).\n" +
                "\n" +
                "Dolce una mousse al maracujá (frutto della passione).\n" +
                "\n" +
                "acqua\n" +
                "birra ghiacciatissima");
        e.setCucina("Brasiliana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position3);
        c.set(2018,3,10,20,0);
        e.setData(c);
        e.addPartecipante("dcont@ium.it");
        e.addPartecipante("msta@ium.it");
        e.addFoto(R.drawable.saboresdobrasil);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 10
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("atur@ium.it");
        e.setTitolo("Que viva Mexico!");
        e.setDescrizione("MENU\n" +
                "\n" +
                "Aperitivo: Tequila sunrise e bicchierini di ceviche (pesce marinato) con nachos di mais\n" +
                "A tavola: tacos di frumento da comporre al momento con chili di carne, formaggio, lime, salse (guacamole, panna acida, salsa rossa piccante) e verdure (fagioli rossi, peperoni, pomodori, cipolla, insalata, sedano, mais, zucca)\n" +
                "Mole poblano (pollo con salsa al cioccolato) servito con riso bollito\n" +
                "Dolce: Flan de vanilla\n" +
                "\n" +
                "E da bere.... birra messicana!");
        e.setCucina("Messicana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position3);
        c.set(2018,3,11,20,30);
        e.setData(c);
        e.addPartecipante("rmam@ium.it");
        e.addPartecipante("msta@ium.it");
        e.addFoto(R.drawable.mexico);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 11
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("atur@ium.it");
        e.setTitolo("Festa dello stocco");
        e.setDescrizione("MENU\n" +
                "Aperitivo di apertura:\n" +
                "Tarallini fatti in casa con olio evo farina integrale e poco sale accompagnato da olive\n" +
                "Caciocavallo semi piccante \n" +
                "a seguire:\n" +
                "Degustazione di zuppa di stoccafisso\n" +
                "carciofi alla romana \n" +
                "Il tutto accompagnato da un calice di vino rosso, e acqua. Per concludere la serata un buon caffè");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position3);
        c.set(2018,3,12,20,45);
        e.setData(c);
        e.addFoto(R.drawable.stocco);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 12
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("atur@ium.it");
        e.setTitolo("Una delizia di Apericena");
        e.setDescrizione("MENU\n" +
                "Taglieri misti di salumi e formaggi accompagnati da ottime crescentine\n" +
                "Assortimento di pizza ai vari gusti\n" +
                "Torta salata della nonna\n" +
                "Panettone salato farcito \n" +
                "Tramezzini \n" +
                "Frittelle dolci e salate\n" +
                "Spiedini di frutta di stagione con gelato al cioccolato\n" +
                "Dolce della casa \n" +
                "Acqua, bibite, vino, caffè");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position3);
        c.set(2018,3,13,19,30);
        e.setData(c);
        e.addPartecipante("dcont@ium.it");
        e.addPartecipante("rmam@ium.it");
        e.addFoto(R.drawable.apericena);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 13
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("msta@ium.it");
        e.setTitolo("Viva Mexico");
        e.setDescrizione(
                "MENU\n" +
                "Aperitivo con uno shot di Tequila o bevanda analcolica\n" +
                "Antipasto a base di Totopos (tortilla chips) con Salse\n" +
                "\n" +
                "Piatto principale: \n" +
                "Sopes (tipico piatto del nord del Messico), \n" +
                "Enchilada (piatto tipico della tradizione culinaria messicana, composto da una tortilla ripiena, arrotolata su se stessa e condita con salsa chili. Il ripieno della tortilla può essere di vari tipi: carne, formaggio, verdura, fagioli)\n" +
                "\n" +
                "Birra\n" +
                "\n" +
                "Caffè");
        e.setCucina("Messicana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Silvio Pellico 18");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position4);
        c.set(2018,3,14,20,30);
        e.setData(c);
        e.addPartecipante("dcont@ium.it");
        e.addPartecipante("atur@ium.it");
        e.addRequest("tcai@ium.it");
        e.addFoto(R.drawable.sopes);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 14
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("msta@ium.it");
        e.setTitolo("Festa brasiliana");
        e.setDescrizione("MENU\n" +
                "Feijoada carioca: stufato di carni varie con salsicce fresche e affumicata e fagioli neri \n" +
                "Riso bianco \n" +
                "Farofa ( farina di manioca condita)\n" +
                "Couve refogada ( bieta ripassata in padella)\n" +
                "\n" +
                "Desert pudim de leite");
        e.setCucina("Brasiliana");
        e.setPranzo_cena(Event.PRANZO);
        e.setVia("Via Silvio Pellico 18");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position4);
        c.set(2018,3,15,13,30);
        e.setData(c);
        e.addPartecipante("rmam@ium.it");
        e.addPartecipante("atur@ium.it");
        e.addPartecipante("ajon@ium.it");
        e.addFoto(R.drawable.festabrasil);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 15
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("msta@ium.it");
        e.setTitolo("Cena Giapponese");
        e.setDescrizione("MENU\n" +
                "Yakiudon con verdure, gamberi e funghi shiitake\n" +
                "Maiale in salsa teriyaki\n" +
                "Sushi: Nigiri Gio, Uramaki special, nigiri ponpon\n" +
                "Acqua, vino e dessert");
        e.setCucina("Giapponese");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Silvio Pellico 18");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position4);
        c.set(2018,3,16,19,0);
        e.setData(c);
        e.addFoto(R.drawable.sushi);
        e.setMaxBookings(4);
        listaEventi.add(e);
        //Evento 16
        e = new Event();
        c = Calendar.getInstance();
        e.setUser("atur@ium.it");
        e.setTitolo("Polentona: e poi c'è Jack");
        e.setDescrizione("MENU\n" +
                "Aperitivo di benvenuto\n" +
                "Antipasto:\n" +
                "Peperone crusco lucano\n" +
                "Formaggi con marmellata fatta in casa.\n" +
                "Salumi lucani.\n" +
                "\n" +
                "Piatto unico\n" +
                "Polenta con ragù salsiccie e funghi\n" +
                "\n" +
                "dolce e frutta di stagione\n" +
                "caffè e JACK DANIEL'S\n" +
                "\n" +
                "Acqua, vino e bibite analcoliche\n" +
                "pane");
        e.setCucina("Italiana");
        e.setPranzo_cena(Event.CENA);
        e.setVia("Via Cagliari 78");
        e.setCity("Quartu Sant'Elena");
        e.setLocation(position3);
        c.set(2018,3,16,20,0);
        e.setData(c);
        e.addFoto(R.drawable.polentona);
        e.setMaxBookings(4);
        listaEventi.add(e);

        //Generazione ID sequenziali
        int i = 0;
        for(Event ev:listaEventi){
            ev.setId(i++);
        }
    }

    public void setPartialEvent(Event partialEvent) {
        this.partialEvent = partialEvent;
    }

    public Event getPartialEvent() {
        return partialEvent;
    }

    public void addEvent(Event event) {
        this.listaEventi.add(event);
    }
}
