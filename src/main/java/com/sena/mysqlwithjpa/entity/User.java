package com.sena.mysqlwithjpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

/**
 * Entidad User que representa la tabla de usuarios en la base de datos MySQL.
 * Incluye validaciones mediante anotaciones de Jakarta Bean Validation (Hibernate Validator).
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    /**
     * Validación: @NotBlank, @Size y @Pattern
     * - @NotBlank: Comprueba que no sea nulo ni esté vacío.
     * - @Size: Longitud entre 3 y 50 caracteres.
     * - @Pattern: Solo permite letras (incluye tildes, ñ) y espacios. Bloquea números y caracteres especiales (/%*+-], etc.).
     */
    @NotBlank(message = "El nombre es obligatorio y no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "El nombre solo puede contener letras y espacios, sin números ni caracteres especiales como (/%*+-]")
    private String name;

    /**
     * Validación: @NotBlank, @Email y @Pattern
     * - @NotBlank: Obligatorio.
     * - @Email y @Pattern: Valida el formato estándar de correo y bloquea caracteres extraños.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar una dirección de correo electrónico válida (ej: usuario@correo.com)")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El formato del correo electrónico no es válido o contiene caracteres no permitidos")
    private String email;

    /**
     * Validación: @NotNull, @Min y @Max
     * - @NotNull: Obligatorio.
     * - @Min(18): Mínimo 18 años.
     * - @Max(90): Máximo 90 años (actualizado de 120 a 90).
     */
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad mínima permitida es 18 años")
    @Max(value = 90, message = "La edad no puede superar los 90 años")
    private Integer age;

    /**
     * Validación: @NotBlank y @Pattern
     * - Exige exactamente 10 dígitos numéricos continuos (0-9). No permite símbolos ni letras.
     */
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono solo puede contener exactamente 10 dígitos numéricos, sin letras ni caracteres especiales")
    private String phone;

    // Constructores
    public User() {
    }

    public User(String name, String email, Integer age, String phone) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}