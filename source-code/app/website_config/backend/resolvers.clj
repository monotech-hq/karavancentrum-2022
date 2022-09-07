
(ns app.website-config.backend.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [pathom.api                            :as pathom]
              [server-fruits.io                      :as io]
              [x.server-core.api                     :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defresolver get-data
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (map)
             [env _]
             {:website-config/get-data (io/read-edn-file a/WEBSITE-CONFIG-FILEPATH)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (vector)
(def HANDLERS [get-data])

(pathom/reg-handlers! ::handlers HANDLERS)
