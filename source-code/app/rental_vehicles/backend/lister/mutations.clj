
(ns app.rental-vehicles.backend.lister.mutations
    (:require [app.common.backend.api                :as common]
              [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn delete-items-f
  ; @param (map) env
  ; @param (map) mutation-props
  ;  {:item-ids (strings in vector)}
  ;
  ; @return (strings in vector)
  [_ {:keys [item-ids]}]
  (mongo-db/remove-documents! "rental-vehicles" item-ids))

(defmutation delete-items!
             ; @param (map) env
             ; @param (map) mutation-props
             ;  {:item-ids (strings in vector)}
             ;
             ; @return (strings in vector)
             [env mutation-props]
             {::pathom.co/op-name 'rental-vehicles.lister/delete-items!}
             (delete-items-f env mutation-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn undo-delete-items-f
  ; @param (map) env
  ; @param (map) mutation-props
  ;  {:items (namespaced maps in vector)}
  ;
  ; @return (namespaced maps in vector)
  [{:keys [items]}]
  (mongo-db/insert-documents! "rental-vehicles" items))

(defmutation undo-delete-items!
             ; @param (map) env
             ; @param (map) mutation-props
             ;  {:items (namespaced maps in vector)}
             ;
             ; @return (namespaced maps in vector)
             [env mutation-props]
             {::pathom.co/op-name 'rental-vehicles.lister/undo-delete-items!}
             (undo-delete-items-f env mutation-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn duplicate-items-f
  ; @param (map) env
  ; @param (map) mutation-props
  ;  {:item-ids (keywords in vector)}
  ;
  ; @return (keywords in vector)
  [{:keys [request]} {:keys [item-ids]}]
  (let [prototype-f #(common/duplicated-document-prototype request :vehicle %)]
       (mongo-db/duplicate-documents! "rental-vehicles" item-ids
                                      {:prototype-f prototype-f})))

(defmutation duplicate-items!
             ; @param (map) env
             ; @param (map) mutation-props
             ;  {:item-ids (keywords in vector)}
             ;
             ; @return (keywords in vector)
             [env mutation-props]
             {::pathom.co/op-name 'rental-vehicles.lister/duplicate-items!}
             (duplicate-items-f env mutation-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [delete-items! undo-delete-items! duplicate-items!])

(pathom/reg-handlers! ::handlers HANDLERS)
