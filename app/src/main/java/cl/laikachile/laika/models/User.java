package cl.laikachile.laika.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import cl.laikachile.laika.utils.DB;

/**
 * Created by Tito_Leiva on 10-02-15.
 */

@Table(name = "users")
public class User extends Model {

    /**[AI]
     * Manejo de atributos como properties que son llamados directamente
     * Se debe agregar el nombre de la columna arriba de cada atributo
     *
     * Es necesario importar el paquete com.activeandroid.annotation.Column para esto
     *
     */

    //FIXME agregar las columnas faltantes
    public static final String COLUMN_USER_ID = "user_id";

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "slug")
    public String slug;

    @Column(name = "email")
    public String email;

    @Column(name = "image")
    public String image;

    @Column(name = "password_salt")
    public String passwordSalt;

    @Column(name = "password_hash")
    public String passwordHash;

    @Column(name = "active")
    public boolean active;

    @Column(name = "admin")
    public boolean admin;

    @Column(name = "first_login")
    public boolean firstLogin;

    @Column(name = "deleted")
    public boolean deleted;

    @Column(name = "sign_in_count")
    public int signInCount;

    @Column(name = "last_ign_in_at")
    public String lastSignInAt;

    @Column(name = "created_at")
    public String createdAt;

    @Column(name = "avatar_file_name")
    public String avatarFileName;

    @Column(name = "avatar_content_type")
    public String avatarContentType;

    @Column(name = "avatar_file_size")
    public int avatarFileSize;

    @Column(name = "avatar_updated_at")
    public String avatarUpdatedAt;

    public User(String firstName, String lastName, String slug, String email,
                String image, String passwordSalt, String passwordHash,
                boolean active, boolean admin, boolean firstLogin, boolean deleted,
                int signInCount, String lastSignInAt, String createdAt,
                String avatarFileName, String avatarContentType,
                int avatarFileSize, String avatarUpdatedAt) {

        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.slug = slug;
        this.email = email;
        this.image = image;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
        this.active = active;
        this.admin = admin;
        this.firstLogin = firstLogin;
        this.deleted = deleted;
        this.signInCount = signInCount;
        this.lastSignInAt = lastSignInAt;
        this.createdAt = createdAt;
        this.avatarFileName = avatarFileName;
        this.avatarContentType = avatarContentType;
        this.avatarFileSize = avatarFileSize;
        this.avatarUpdatedAt = avatarUpdatedAt;
    }

    public User() {	}

}