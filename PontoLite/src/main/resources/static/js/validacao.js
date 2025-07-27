function formatarCNPJ(cnpj) {
  cnpj = cnpj.replace(/\D/g, ''); // Remove caracteres não numéricos
  if (cnpj.length === 14) {
    return cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g, "\$1.\$2.\$3/\$4-\$5");
  }
  return cnpj; // Retorna o CNPJ sem formatação se não tiver 14 dígitos
}

