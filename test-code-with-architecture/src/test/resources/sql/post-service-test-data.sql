
insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (1, 'kimjisu3268@gmail.com', 'jisu3268', 'Seoul', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ACTIVE', 0);
insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (2, 'kimjisu1234@gmail.com', 'jisu1234', 'Seoul', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', 'PENDING', 0);
insert into `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`)
values (1, 'helloworld', 1678530673958, 0, 1);