# projetoAlgoritmoDeCristian

O projeto contém um cliente-servidor, no qual duas threads diferentes executam simultaneamente o cliente e o servidor, o servidor possui um socket no qual recebe requisições do cliente após um intervalo de tempo. 

Seguindo a ideia do algoritmo proposto, o cliente pega o horário atual, coloca um delay de tempo e envia uma requisição com o horário definido ao servidor. O servidor recebe a requisição com o tempo fornecido pelo cliente e retorna para o cliente o horário recebido, juntamente com o horário do cliente mais o tempo de espera do servidor, e o horário com o tempo que o servidor respondeu o cliente. Posteriormente, o cliente recebe a resposta do servidor com todos os três tempos citados e realiza o cálculo do algoritmo de Cristian para definir o horário atual.    
