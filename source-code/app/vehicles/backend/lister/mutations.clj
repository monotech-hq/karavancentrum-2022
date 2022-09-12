
(ns app.vehicles.backend.lister.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation delete-items!
             [{:keys [item-ids]}]
             {::pathom.co/op-name 'vehicles.lister/delete-items!}
             (mongo-db/remove-documents! "vehicles" item-ids))

(defmutation undo-delete-items!
             [{:keys [items]}]
             {::pathom.co/op-name 'vehicles.lister/undo-delete-items!}
             (mongo-db/insert-documents! "vehicles" items))

(defmutation duplicate-items!
             [{:keys [request]} {:keys [item-ids]}]
             {::pathom.co/op-name 'vehicles.lister/duplicate-items!}
             (mongo-db/duplicate-documents! "vehicles" item-ids
                                            {:prototype-f #(mongo-db/duplicated-document-prototype request :vehicle %)}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def HANDLERS [delete-items! undo-delete-items! duplicate-items!])

(pathom/reg-handlers! ::handlers HANDLERS)
