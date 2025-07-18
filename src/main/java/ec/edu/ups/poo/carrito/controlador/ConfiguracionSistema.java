package ec.edu.ups.poo.carrito.controlador;

public class ConfiguracionSistema {

    public enum TipoAlmacenamiento { MEMORIA, ARCHIVOS }

    private static ConfiguracionSistema instancia;
    private TipoAlmacenamiento tipoAlmacenamiento;
    private String rutaArchivos;

    private ConfiguracionSistema() {}

    public static ConfiguracionSistema getInstancia() {
        if (instancia == null) instancia = new ConfiguracionSistema();
        return instancia;
    }

    public TipoAlmacenamiento getTipoAlmacenamiento() {
        return tipoAlmacenamiento;
    }

    public void setTipoAlmacenamiento(TipoAlmacenamiento tipoAlmacenamiento) {
        this.tipoAlmacenamiento = tipoAlmacenamiento;
    }

    public String getRutaArchivos() {
        return rutaArchivos;
    }

    public void setRutaArchivos(String rutaArchivos) {
        this.rutaArchivos = rutaArchivos;
    }
}
