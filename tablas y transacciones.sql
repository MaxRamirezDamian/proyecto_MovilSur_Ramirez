/*
Tabla USERS
 */
create table
     DEVELOPER_FATAMA.USERS (
          user_id raw(16) default sys_guid() primary key,
          firebase_id varchar2(50),
          first_name varchar2(100) not null check (regexp_like (first_name, '^[^0-9]+$')),
          last_name varchar2(100) not null check (regexp_like (last_name, '^[^0-9]+$')),
          document_type char(3) not null check (document_type in ('DNI', 'CNE')),
          document_number varchar2(20) unique not null check (length(document_number) in (8, 20)),
          email varchar2(100) unique not null check (
               regexp_like (
                    email,
                    '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
               )
          ),
          phone varchar2(15) not null check (length(phone) = 9),
          address varchar2(100),
          user_photo varchar2(255),
          username varchar2(100) not null,
          password varchar2(100) not null,
          created_at timestamp default current_timestamp,
          updated_at timestamp,
          user_type varchar2(20) default 'CLIENT' check (user_type in ('CLIENT', 'ADMIN', 'DRIVER')),
          status varchar2(10) default 'ACTIVE' check (status in ('ACTIVE', 'INACTIVE'))
     );

/*
Tabla DRIVERS
 */
create table
     DEVELOPER_BEINGOLEA.DRIVERS (
          driver_id raw(16) default sys_guid() primary key,
          user_id raw(16) not null unique,
          license_type varchar2(2) not null check (license_type in ('A', 'B', 'C', 'D', 'E', 'F')),
          license_category varchar2(3) check (license_category in ('I', 'II', 'III')),
          license_number varchar2(9) not null unique check (regexp_like (license_number, '^[A-Z0-9]{9}$')),
          license_expiration date not null,
          experience_years number,
          available char(1) default 'N' check (available in ('Y', 'N')),
          license_class varchar2(20) check (
               license_class in ('NO PROFESIONAL', 'PROFESIONAL')
          ),
          status varchar2(10) default 'ACTIVE' check (status in ('ACTIVE', 'INACTIVE')),
          status_register varchar2(15) default 'PENDIENTE' check(status_register in ('PENDIENTE', 'ACEPTADO', 'RECHAZADO'))

     );

/*
Tabla VEHICLES
 */
create table
     DEVELOPER_RAMIREZ.VEHICLES (
          vehicle_id raw(16) default sys_guid() primary key,
          driver_id raw(16) not null,
          license_plate varchar2(10) not null unique check (
               regexp_like (license_plate, '^[A-Z]{3}-[0-9]{3}$')
          ),
          brand varchar2(50) not null check (regexp_like (brand, '^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$')),
          model varchar2(50) not null check (
               regexp_like (model, '^[A-Za-zñÑáéíóúÁÉÍÓÚ0-9 ]+$')
          ),
          year number not null check (year >= 2000),
          color varchar2(20) not null check (regexp_like (color, '^[A-Za-zñÑáéíóúÁÉÍÓÚ ]+$')),
          vehicle_photo varchar2(250),
          seat_count number(2) check (seat_count between 4 and 7),
          vehicle_status varchar2(20) default 'DISPONIBLE' check (
               vehicle_status in ('DISPONIBLE', 'EN_MANTENIMIENTO', 'OCUPADO')
          ),
          registration_date timestamp default current_timestamp,
          status varchar2(10) default 'ACTIVE' check (status in ('ACTIVE', 'INACTIVE')),
          vehicle_type varchar2(20) not null check (vehicle_type in ('AUTO', 'CAMIONETA', 'MINIVAN')),
          fuel_type varchar2(20) not null check (
               fuel_type in (
                    'GASOLINA',
                    'DIESEL',
                    'GAS',
                    'ELECTRICO',
                    'HIBRIDO'
               )
          )
     );

-- CREA UNA TABLA DE METODO DE PAGOS
create table
     DEVELOPER_RAMIREZ.PAYMENT_METHOD (
          payment_method_id raw(16) default sys_guid() primary key,
          payment_type varchar2(50) not null check (
               payment_type in ('EFECTIVO', 'YAPE', 'PLIN', 'TRANSFERENCIA')
          ),
          status varchar2(10) default 'ACTIVE' check (status in ('ACTIVE', 'INACTIVE'))
     );

create table
     DEVELOPER_FATAMA.SERVICE_TYPES (
          service_type_id raw(16) default sys_guid() primary key,
          name varchar2(50) not null,
          description varchar2(255),
          base_cost number(10, 2) not null,
          cost_per_km number(10, 2) not null,
          cost_per_minute number(10, 2) not null,
          minimum_cost number(10, 2) not null,
          status varchar2(10) default 'ACTIVE' check (status in ('ACTIVE', 'INACTIVE'))
     );

create table
     DEVELOPER_RAMIREZ.TRIP (
          trip_id raw(16) default sys_guid() primary key,
          user_id raw(16) not null,
          driver_id raw(16) not null,
          vehicle_id raw(16) not null,
          origin_address varchar2(150) not null,
          destination_address varchar2(150) not null,
          started_at timestamp,
          ended_at timestamp,
          seats_used number(2) not null check (seats_used between 1 and 7),
          payment_method_id raw(16) not null,
          total_cost number(10, 2) not null,
          trip_status varchar2(20) default 'EN_CURSO' check (
               trip_status in ('EN_CURSO', 'COMPLETADO', 'CANCELADO')
          ),
          created_at timestamp default current_timestamp not null,
          updated_at timestamp,
          service_type_id raw(16),
          distance_meters number,
          duration_seconds number
     );

create table
     DEVELOPER_RAMIREZ.TRIP_DETAILS (
          trip_details_id raw(16) default sys_guid() primary key,
          trip_id raw(16) not null,
          description varchar2(300),
          quantity number(3) default 1 check (quantity >= 1),
          is_fragile char(1) default 'N' check (is_fragile in ('Y', 'N')),
          created_at timestamp default current_timestamp not null
     );

/*
TRANSACCIONAL 2: Reservas (RESERVATION y RESERVATION_DETAIL) - Para viajes futuros
 */
create table
     DEVELOPER_FATAMA.RESERVATION (
          reservation_id raw(16) default sys_guid() primary key,
          user_id raw(16) not null,
          driver_id raw(16),
          vehicle_id raw(16),
          origin_address varchar2(150) null,
          destination_address varchar2(150) null,
          scheduled_date date not null,
          scheduled_time timestamp not null,
          seats_requested number(2) not null check (seats_requested between 1 and 7),
          payment_method_id raw(16) not null,
          total_cost number(10, 2) not null,
          reservation_status varchar2(20) default 'PENDIENTE' check (
               reservation_status in (
                    'PENDIENTE',
                    'CONFIRMADA',
                    'EN_CURSO',
                    'COMPLETADA',
                    'CANCELADA'
               )
          ),
          created_at timestamp default current_timestamp not null,
          updated_at timestamp,
          service_type_id raw(16),
          distance_meters number,
          duration_seconds number
     );

create table
     DEVELOPER_FATAMA.RESERVATION_DETAIL (
          reservation_detail_id raw(16) default sys_guid() primary key,
          reservation_id raw(16) not null,
          stop_order number(2) not null,
          stop_description varchar2(250) not null
     );

/*
Tabla SHIPPMENT
 */
create table
     DEVELOPER_BEINGOLEA.SHIPMENTS (
          shipment_id raw(16) default sys_guid() primary key,
          user_id raw(16) not null,
          driver_id raw(16),
          vehicle_id raw(16),
          pickup_address varchar2(150) not null,
          description_address varchar2(255),
          delivery_date date,
          hour_shipments timestamp,
          total_cost number(10, 2),
          status varchar2(50) default 'PENDIENTE' check (
               status in (
                    'PENDIENTE',
                    'EN_CAMINO',
                    'COMPLETADO',
                    'CANCELADO',
                    'ENTREGA_PARCIAL',
                    'ENTREGA_INCOMPLETA'
               )
          ),
          created_at timestamp default current_timestamp,
          updated_at timestamp
     );

/*
Tabla SHIPPMENT_ITEM
 */
create table
     DEVELOPER_BEINGOLEA.SHIPPMENT_DETAIL (
          ship_detail_id raw(16) default sys_guid() primary key,
          shipment_id raw(16) not null,
          package_description varchar2(255),
          destination_address varchar2(150) not null,
          dimensions varchar2(50) check (
               regexp_like (dimensions, '^[0-9]+x[0-9]+x[0-9]+(cm|m)$')
          ),
          recipient_name varchar2(100),
          recipient_phone varchar2(15),
          recipient_document_number varchar2(20) check (length(recipient_document_number) in (8, 20)),
          quantity number default 1 check (quantity > 0),
          delivery_status varchar2(50) default 'PENDIENTE' check (delivery_status in ('PENDIENTE', 'ENTREGADO','CANCELADO')),
          cost_package number(10, 2) check (cost_package > 0)
     );
