package ec.edu.ups.poo.carrito.util;

public class ConfiguracionSistema {
    /**
     * Enum que representa los tipos de almacenamiento disponibles para el sistema:
     * <ul>
     *   <li>{@code MEMORIA}: Almacenamiento volátil en memoria RAM.</li>
     *   <li>{@code ARCHIVOS}: Almacenamiento persistente en archivos de texto plano.</li>
     *   <li>{@code ARCHIVOS_BINARIOS}: Almacenamiento persistente en archivos binarios.</li>
     * </ul>
     */
    public enum TipoAlmacenamiento {
        MEMORIA, ARCHIVOS, ARCHIVOS_BINARIOS
    }

    private static ConfiguracionSistema instancia;
    private TipoAlmacenamiento tipoAlmacenamiento;
    private String rutaArchivos;

    private ConfiguracionSistema() {
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
