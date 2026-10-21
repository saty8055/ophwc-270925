# OPHWC Application – Server Deployment Requirements

Dear Team,

Please ensure the following dependencies are installed and configured on the server before deploying the **OPHWC** application.

---

## 1. System Dependencies

| #  | Dependency     | Version   | Notes                                              |
|----|----------------|-----------|----------------------------------------------------|
| 1  | Java JDK       | 8 (1.8)   | Must be Java 8 exactly                             |
| 2  | Apache Tomcat  | 7.x       | Deploy on port `8055`, context path `/ophwc`       |
| 3  | MySQL Server   | 8.0       | Port `3306`                                        |
| 4  | Apache Maven   | 3.x       | Required only if building the `.war` from source   |

---

## 2. Database Configuration

| #  | Item           | Value           |
|----|----------------|-----------------|
| 1  | Database Name  | `ophwc`         |
| 2  | Host           | `localhost`     |
| 3  | Port           | `3306`          |
| 4  | Username       | `ophwcapp`      |
| 5  | Password       | `Quantum@123`   |

---

## 3. Deployment Steps

1. Install Java JDK 8 and set `JAVA_HOME` environment variable.
2. Install and configure Apache Tomcat 7.x.
3. Install MySQL 8.0 and create the database and user as per the configuration above.
4. Place the `ophwc.war` file in the Tomcat `webapps` directory.
5. Start Tomcat and access the application at: `http://<server-ip>:8055/ophwc`

---

> **Note:** All Java library dependencies (Spring, Hibernate, Jersey, etc.) are bundled inside the `.war` file and do not require separate installation on the server.

---

Regards,
GCS Team

