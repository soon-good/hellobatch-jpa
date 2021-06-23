create table IF NOT EXISTS book
(
    name varchar(100),
    id   serial not null
        constraint book_pk
            primary key,
    price integer
);

alter table book
    owner to testadmin;

INSERT INTO public.book (name, id) VALUES ('시인은 저녁에 감성이 돋는다', 1);
INSERT INTO public.book (name, id) VALUES ('오늘은 이만 쉴께요', 2);
INSERT INTO public.book (name, id) VALUES ('사업왕 심길후', 3);
INSERT INTO public.book (name, id) VALUES ('빅데이터 저장 및 분석을 위한 NoSQL & Redis', 4);
INSERT INTO public.book (name, id) VALUES ('Redis 운영 관리', 5);