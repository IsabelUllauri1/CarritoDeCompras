package ec.edu.ups.poo.carrito.util;

public class ConfiguracionSistema {

    public enum TipoAlmacenamiento {
        MEMORIA, ARCHIVOS, ARCHIVOS_BINARIOS
    }

    private static ConfiguracionSistema instancia;

    private TipoAlmacenamiento tipoAlmacenamiento;
    private String rutaArchivos;

    private ConfiguracionSistema() {
        // por defecto en memoria
        this.tipoAlmacenamiento = TipoAlmacenamiento.MEMORIA;
        this.rutaArchivos = "";
    }

    public static ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
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
