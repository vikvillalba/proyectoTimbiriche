package Observer;


/**
 *
 * @author erika
 */
public interface ObservableEventos {

    void notificarEventoRecibido(Object evento);

    void agregarObservadorEventos(ObservadorEventos ob);
    
}
