-- Esse é o Seeder para os exercícios. Ele vai servir como que um "warm-start", para você, usuário/desenvolvedor, criar seus próprios exercícios. Aproveite!
INSERT INTO exercicio (id, nome, descricao, categoria_exercicio, grupo_muscular) VALUES
(uuid_generate_v4(), 'Supino Reto', 'Exercício de peito utilizando barra ou halteres', 'FORCA', 'PEITO'),
(uuid_generate_v4(), 'Agachamento', 'Exercício de pernas para quadríceps e glúteos', 'FORCA', 'PERNAS'),
(uuid_generate_v4(), 'Levantamento Terra', 'Exercício de força para costas e pernas', 'FORCA', 'COSTAS'),
(uuid_generate_v4(), 'Rosca Direta', 'Exercício de bíceps com barra ou halteres', 'FORCA', 'BICEPS'),
(uuid_generate_v4(), 'Tríceps Pulley', 'Exercício de tríceps na máquina de cabo', 'FORCA', 'TRICEPS'),
(uuid_generate_v4(), 'Elevação Lateral', 'Exercício de ombros para deltoides laterais', 'FORCA', 'OMBROS'),
(uuid_generate_v4(), 'Prancha', 'Exercício de core para abdominal e lombar', 'FLEXIBILIDADE', 'CORE'),
(uuid_generate_v4(), 'Corrida na Esteira', 'Exercício cardiovascular de baixa intensidade', 'CARDIO', 'CORE'),
(uuid_generate_v4(), 'Alongamento de Pernas', 'Alongamento para flexibilidade de pernas e quadríceps', 'FLEXIBILIDADE', 'PERNAS'),
(uuid_generate_v4(), 'Flexão de Braço', 'Exercício de força de peito e tríceps usando o próprio peso', 'FORCA', 'PEITO');
