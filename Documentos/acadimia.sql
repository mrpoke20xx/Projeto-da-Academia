create table academia (
aca_codigo int not null auto_increment,
aca_nome varchar not null,
aca_endereco varchar not null,
aca_fone varchar,
primary key (aca_codigo)
);
create table equipamento (
equ_codigo int not null auto_increment,
equ_nome varchar not null,
equ_pesomax double not null,
equ_descricao varchar,
equ_aca_codigo int,
primary key (equ_codigo),
foreign key (equ_aca_codigo)
references academico (aca_codigo)
);
create table exercicio (
exe_codigo int not null auto_increment,
exe_nome varchar not null,
exe_descricao varchar not null,
exe_equ_cod int not null,
primary key (exe_codigo),
foreign key (exe_equ_codigo)
references equipamento(equ_codigo)
);
create table trainer (
tra_codigo int not null auto_increment,
tra_nome varchar not null,
tra_data_nasc date not null,
tra_sexo char(1) not null,
tra_aca_codigo int not null,
primary key (tra_codigo),
foreign key (tra_aca_codigo)
references academia (aca_codigo)
);
create table cliente (
cli_codigo int not null auto_increment,
cli_nome varchar not null,
cli_endereco varchar not null,
cli_data_nasc date not null,
cli_sexo char(1),
cli_necessidade varchar,
cli_tra_codigo int not null,
primary key (cli_codigo),
foreign key (cli_tra_codigo)
references trainer (tra_codigo)
);
create table treino (
tre_codigo int not null auto_increment,
tre_vencimento varchar not null,
tre_cli_codigo int not null,
tre_exe_codigo int not null,
primary key (tre_codigo),
foreign key (tre_cli_codigo)
references cliente (cli_codigo),
foreign key (tre_exe_codigo)
references exercicio (exe_codigo)
);
create table progresso (
pro_cli_codigo int not null,
pro_data date not null,
pro_cli_massa double not null,
pro_cli_altura double not null,
foreign key (pro_cli_codigo)
references cliente (cli_codigo)
);
