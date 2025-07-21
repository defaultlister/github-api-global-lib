def call(List<Map> secretsList = [], Closure body) {
    if (!secretsList || !(secretsList instanceof List)) {
        error("Expected a list of secrets with at least one entry (each with 'id' and 'prefix').")
    }

    def secrets = secretsList.collect { entry ->
        if (!entry.id || !entry.prefix) {
            error("Each secret must have 'id' and 'prefix' defined.")
        }

        def vaultUrl = entry.get('vaultUrl', 'https://your.delinea.secretserver')
        def credentialId = entry.get('credentialId', 'delinea_jenkins')
        def fieldSlugs = entry.get('fieldSlugs', ['Username', 'Password'])

        def mappings = fieldSlugs.collect { field ->
            [field: field, environmentVariable: "${entry.prefix}_${field.toUpperCase()}"]
        }

        return [
            id          : entry.id,
            vaultUrl    : vaultUrl,
            credentialId: credentialId,
            fieldSlugs  : fieldSlugs,
            mappings    : mappings
        ]
    }

    wrap([$class: 'ServerBuildWrapper', secrets: secrets]) {
        body()
    }
}
