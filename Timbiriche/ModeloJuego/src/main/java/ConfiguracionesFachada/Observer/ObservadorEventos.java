package ConfiguracionesFachada.Observer;

/**
 *
 * @author victoria
 */
public interface ObservadorEventos<T> {
    void actualizar(T cambio);
}
