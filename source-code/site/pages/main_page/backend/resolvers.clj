
(ns site.pages.main-page.backend.resolvers
  (:require
    [com.wsscode.pathom3.connect.operation :refer [defresolver]]
    [pathom.api                            :as pathom]
    [server-fruits.io                      :as io]
    [x.server-core.api                     :as a]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def FILE_PATH "environment/db/contents.edn")

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Resolvers ----

(defresolver get!
  [env _]
  {:main-page/get! (io/read-edn-file FILE_PATH)})

;; ---- Resolvers ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Registry ----

(def HANDLERS [get!])

(pathom/reg-handlers! ::handlers HANDLERS)

;; ---- Registry ----
;; -----------------------------------------------------------------------------
