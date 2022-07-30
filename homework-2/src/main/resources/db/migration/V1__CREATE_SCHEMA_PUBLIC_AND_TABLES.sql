create table if not exists public.users
(
    id  integer not null primary key,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    middle_name varchar(255),
    full_name   varchar(255),
    email       varchar(255) not null unique,
    password    varchar(255) not null
);
comment on table public.users is 'Пользователи';
comment on column public.users.first_name is 'Имя пользователя';
comment on column public.users.last_name is 'Фамилия пользователя';
comment on column public.users.middle_name is 'Отчество пользователя';
comment on column public.users.full_name is 'Полное ФИО пользователя';

create table if not exists public.projects
(
    id integer not null primary key,
    name        varchar(255) not null unique,
    description varchar(255) not null,
    start_date  date not null,
    finish_date date not null
);
comment on table public.projects is 'Проекты';

create table if not exists public.tasks
(
    id integer not null primary key,
    deadline    date not null,
    description varchar(255) not null,
    name        varchar(255) not null unique,
    status      varchar(255),
    project_id integer constraint fk_task_project references public.projects(id),
    user_id integer constraint fk_task_user references public.users(id)
);
comment on table public.tasks is 'Задачи';
comment on column public.tasks.deadline is 'Крайний день сдачи задачи';

create table if not exists public.comments
(
    id integer not null primary key,
    comment text not null,
    task_id integer constraint fk_comment_task references public.tasks(id),
    user_id integer constraint fk_comment_user references public.users(id)
);
comment on table public.comments is 'Комментарии к задачам';

create table public.projects_users
(
    user_id integer not null constraint fk_cross_users references public.projects(id),
    project_id integer not null constraint fk_cross_projects references public.users(id),
    primary key (user_id, project_id)
);
comment on table public.projects_users is 'Кросс-таблица Проект-Пользователь многие-ко-многим';