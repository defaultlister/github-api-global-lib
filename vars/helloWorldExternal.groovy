def call(List<Map> secretsList = [], Closure body) {
    if (!secretsList || !(secretsList instanceof List)) {
        error("Expected a list of secrets with at least one entry (each with 'id' and 'prefix').")
    }

    def secrets = secretsList.collect { entry ->
        if (!entry.id || !entry.prefix) {
            error("Each secret must have 'id' and 'prefix' defined.")
        }

        def credentialId = entry.get('credentialId', 'delinea_jenkins')
        def fields = entry.get('fields', ['Username', 'Password']) // Plugin does NOT support 'fieldSlugs'

        def mappings = fields.collect { field ->
            [
                field               : field,
                environmentVariable: "${entry.prefix}_${field.toUpperCase()}"
            ]
        }

        return [
            $class       : 'com.delinea.secrets.jenkins.wrapper.cred.ServerSecret',
            id           : entry.id,
            credentialId : credentialId,
            mappings     : mappings
        ]
    }

    wrap([$class: 'com.delinea.secrets.jenkins.wrapper.cred.ServerBuildWrapper', secrets: secrets]) {
        body()
    }
}
